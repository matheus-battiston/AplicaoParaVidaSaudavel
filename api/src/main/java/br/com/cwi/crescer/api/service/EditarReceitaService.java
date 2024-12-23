package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.ReceitaRequest;
import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.domain.ReceitaAlimento;
import br.com.cwi.crescer.api.repository.ReceitaAlimentoRepository;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.UsuarioDonoReceitaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EditarReceitaService {
    @Autowired
    private BuscarReceitaService buscarReceitaService;

    @Autowired
    private AtualizarReceitaService atualizarReceitaService;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private BuscarAlimentoService buscarAlimentoService;

    @Autowired
    private UsuarioDonoReceitaValidator usuarioDonoReceitaValidator;

    @Autowired
    private ReceitaAlimentoRepository receitaAlimentoRepository;

    @Transactional
    public void editar(Long id, ReceitaRequest request){
        Usuario usuario = usuarioAutenticadoService.get();
        Receita receita = buscarReceitaService.porId(id);
        usuarioDonoReceitaValidator.validar(receita.getUsuario(), usuario);
        receitaAlimentoRepository.deleteAllByReceita_Id(receita.getId());

        List<ReceitaAlimento> alimentos = request.getAlimentos().stream().map(
                alimento -> {
                    ReceitaAlimento receitaAlimento = new ReceitaAlimento();
                    Alimento alimentoAAdicionar = buscarAlimentoService.porId(alimento.getId());
                    receitaAlimento.setReceita(receita);
                    receitaAlimento.setAlimento(alimentoAAdicionar);
                    receitaAlimento.setQuantidade(alimento.getQuantidade());

                    receitaAlimentoRepository.save(receitaAlimento);

                    return receitaAlimento;
                }
        ).collect(Collectors.toList());

        receita.setDescricao(request.getDescricao());
        receita.setPrivado(request.getPrivado());
        receita.setTitulo(request.getTitulo());
        receita.setImagem(request.getImagem());
        receita.setModoPreparo(request.getModoPreparo());
        receita.setCopia(request.getCopia());
        receita.setAlimentos(alimentos);

        atualizarReceitaService.atualizar(receita);
        receitaRepository.save(receita);
    }
}
