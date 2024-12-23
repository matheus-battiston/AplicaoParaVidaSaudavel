package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.RefeicaoAlimentoRequest;
import br.com.cwi.crescer.api.controller.request.RefeicaoRequest;
import br.com.cwi.crescer.api.controller.response.AlimentoResponse;
import br.com.cwi.crescer.api.controller.response.RefeicaoResponse;
import br.com.cwi.crescer.api.domain.Alimento;
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

import static br.com.cwi.crescer.api.mapper.RefeicaoMapper.toEntity;
import static br.com.cwi.crescer.api.mapper.RefeicaoMapper.toResponse;
import static java.util.Objects.isNull;

@Service
public class AdicionarAlimentoRefeicaoService {
    public static final Long PONTOS_POR_ADICIONAR_ALIMENTO_FORA_DE_STREAK = 20L;
    public static final Long PONTOS_POR_ADICIONAR_ALIMENTO_EM_STREAK = 40L;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private BuscarAlimentoService buscarAlimentoService;

    @Autowired
    private RefeicaoRepository refeicaoRepository;

    @Autowired
    private RefeicaoAlimentoRepository refeicaoAlimentoRepository;

    @Autowired
    private AtualizarRefeicaoService atualizarRefeicaoService;

    @Autowired
    private NowService nowService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ConquistasService conquistasService;
    @Transactional
    public RefeicaoResponse adicionar(RefeicaoRequest request){
        Usuario usuario = usuarioAutenticadoService.get();
        Long pontosPorAdicionarAlimento;
        LocalDate hoje = nowService.nowDate();
        Refeicao refeicao = refeicaoRepository
                .findByPeriodoEqualsAndDiaEquals(request.getPeriodo(), request.getDia())
                .orElse(toEntity(request));
        boolean adicionouRefeicaoHoje = refeicaoRepository.existsByDia(hoje);
        boolean adicionouRefeicaoOntem = refeicaoRepository.existsByDia(hoje.minusDays(1));

        if(isNull(refeicao.getId())){
            refeicao.setUsuario(usuario);
        }

        if(adicionouRefeicaoOntem){
            pontosPorAdicionarAlimento = PONTOS_POR_ADICIONAR_ALIMENTO_EM_STREAK;
        } else pontosPorAdicionarAlimento = PONTOS_POR_ADICIONAR_ALIMENTO_FORA_DE_STREAK;

        RefeicaoAlimentoRequest alimentoRequest = request.getAlimento();

        Alimento alimentoAInserir = buscarAlimentoService.porId(alimentoRequest.getId());

        RefeicaoAlimento refeicaoAlimento = refeicao.getAlimentos().stream()
                .filter(alimento -> alimento.getAlimento().getId().equals(alimentoRequest.getId()))
                .findFirst().orElse(new RefeicaoAlimento());

        refeicaoAlimento.setAlimento(alimentoAInserir);
        refeicaoAlimento.setRefeicao(refeicao);
        refeicaoAlimento.setQuantidade(alimentoRequest.getQuantidade());

        if(isNull(refeicaoAlimento.getId())){
            refeicao.adicionarAlimento(refeicaoAlimento);
        }

        refeicaoAlimentoRepository.save(refeicaoAlimento);

        atualizarRefeicaoService.atualizar(refeicao);

        usuario.adicionarRefeicao(refeicao);

        RefeicaoResponse response = toResponse(refeicao);

                response.setAlimentos(refeicao.getAlimentos().stream()
                .map(alimento -> {
                    AlimentoResponse responseAlimento = AlimentoMapper.toResponse(alimento.getAlimento());
                    responseAlimento.setQuantidade(alimento.getQuantidade());
                    return responseAlimento;
                })
                .collect(Collectors.toList()));

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
