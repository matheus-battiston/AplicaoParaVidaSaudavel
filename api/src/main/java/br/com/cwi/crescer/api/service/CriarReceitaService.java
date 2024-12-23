package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.ReceitaRequest;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.domain.ReceitaAlimento;
import br.com.cwi.crescer.api.mapper.ReceitaMapper;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static br.com.cwi.crescer.api.domain.TipoConquista.RECEITAS;

@Service
public class CriarReceitaService {
    public static final Integer NOTA_INICIAL = 0;
    public static final Long PONTOS_POR_NOVA_RECEITA = 50L;
    @Autowired
    private BuscarAlimentoService buscarAlimento;
    @Autowired
    private AtualizarReceitaService atualizarReceitaService;
    @Autowired
    private ReceitaRepository receitaRepository;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private ConquistasService conquistasService;

    @Autowired
    private NowService nowService;

    @Transactional
    public void criar(ReceitaRequest request) {
        Usuario logado = usuarioAutenticadoService.get();
        Receita receita = ReceitaMapper.toEntity(request);
        receita.setAlimentos(
                request.getAlimentos().stream().map(
                        alimento -> {
                            ReceitaAlimento receitaAlimento = new ReceitaAlimento();
                            receitaAlimento.setReceita(receita);
                            receitaAlimento.setAlimento(buscarAlimento.porId(alimento.getId()));
                            receitaAlimento.setQuantidade(alimento.getQuantidade());
                            receita.adicionarAlimento(buscarAlimento.porId(alimento.getId()), receitaAlimento.getQuantidade());
                            return receitaAlimento;
                        }
                        ).collect(Collectors.toList()));
        receita.setUsuario(logado);
        receita.setCriador(logado);
        receita.setNota(NOTA_INICIAL.toString());

        if (primeiraReceitaDoDia(logado)){
            logado.adicionarPontos(PONTOS_POR_NOVA_RECEITA);
            conquistasService.pontos(logado);
        }

        atualizarReceitaService.atualizar(receita);
        logado.adicionarReceita(receita);
        conquistasService.conquistasQuantidadeDeAcoes(logado, RECEITAS);


        receitaRepository.save(receita);
    }

    private boolean primeiraReceitaDoDia(Usuario usuario){
        LocalDate hoje = nowService.nowDate();
        return !receitaRepository.existsByUsuarioAndDataCriacao(usuario, hoje);
    }
}
