package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.RefeicaoPorReceitaRequest;
import br.com.cwi.crescer.api.controller.response.AlimentoResponse;
import br.com.cwi.crescer.api.controller.response.RefeicaoResponse;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.domain.RefeicaoAlimento;
import br.com.cwi.crescer.api.mapper.AlimentoMapper;
import br.com.cwi.crescer.api.repository.RefeicaoAlimentoRepository;
import br.com.cwi.crescer.api.repository.RefeicaoRepository;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static br.com.cwi.crescer.api.mapper.RefeicaoMapper.toResponse;
import static br.com.cwi.crescer.api.mapper.RefeicaoPorReceitaMapper.toEntity;
import static br.com.cwi.crescer.api.service.AdicionarAlimentoRefeicaoService.PONTOS_POR_ADICIONAR_ALIMENTO_EM_STREAK;
import static br.com.cwi.crescer.api.service.AdicionarAlimentoRefeicaoService.PONTOS_POR_ADICIONAR_ALIMENTO_FORA_DE_STREAK;
import static java.util.Objects.isNull;

@Service
public class AdicionarAlimentoRefeicaoPorReceitaService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private BuscarReceitaService buscarReceitaService;

    @Autowired
    private RefeicaoAlimentoRepository refeicaoAlimentoRepository;

    @Autowired
    private AtualizarRefeicaoService atualizarRefeicaoService;

    @Autowired
    private RefeicaoRepository refeicaoRepository;

    @Autowired
    private NowService nowService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConquistasService conquistasService;

    @Transactional
    public RefeicaoResponse adicionar(RefeicaoPorReceitaRequest request){
        Usuario usuario = usuarioAutenticadoService.get();
        LocalDate hoje = nowService.nowDate();
        Long pontosPorAdicionarAlimento;
        Receita receita = buscarReceitaService.porId(request.getIdReceita());
        Refeicao refeicao = refeicaoRepository
                .findByPeriodoEqualsAndDiaEquals(request.getPeriodo(), request.getDia())
                .orElse(toEntity(request));
        boolean adicionouRefeicaoHoje = refeicaoRepository.existsByDia(hoje);
        boolean adicionouRefeicaoOntem = refeicaoRepository.existsByDia(hoje.minusDays(1));

        if (isNull(refeicao.getUsuario())){
          refeicao.setUsuario(usuario);
        };

        if(adicionouRefeicaoOntem){
            pontosPorAdicionarAlimento = PONTOS_POR_ADICIONAR_ALIMENTO_EM_STREAK;
        } else pontosPorAdicionarAlimento = PONTOS_POR_ADICIONAR_ALIMENTO_FORA_DE_STREAK;

        receita.getAlimentos().forEach(
                alimento -> {
                    RefeicaoAlimento refeicaoAlimento = refeicao.getAlimentos().stream()
                            .filter(alimentoRefeicao -> alimentoRefeicao.getAlimento().getId().equals(alimento.getAlimento().getId()))
                            .findFirst().orElse(new RefeicaoAlimento());

                    refeicaoAlimento.setAlimento(alimento.getAlimento());
                    refeicaoAlimento.setRefeicao(refeicao);
                    refeicaoAlimento.setQuantidade(alimento.getQuantidade());

                    if(isNull(refeicaoAlimento.getId())){
                        refeicao.adicionarAlimento(refeicaoAlimento);
                    }

                    refeicaoAlimentoRepository.save(refeicaoAlimento);
                });

        atualizarRefeicaoService.atualizar(refeicao);

        RefeicaoResponse response = toResponse(refeicao);

        response.setAlimentos(refeicao.getAlimentos().stream()
                .map(alimento -> {
                    AlimentoResponse responseAlimento = AlimentoMapper.toResponse(alimento.getAlimento());
                    responseAlimento.setQuantidade(alimento.getQuantidade());
                    return responseAlimento;
                })
                .collect(Collectors.toList()));

        usuario.adicionarRefeicao(refeicao);

        if(!adicionouRefeicaoHoje
                && request.getDia().isEqual(nowService.nowDate())){
            usuario.adicionarPontos(pontosPorAdicionarAlimento);
            conquistasService.pontos(usuario);
            usuarioRepository.save(usuario);
        }

        refeicaoRepository.save(refeicao);

        return response;
    }

}
