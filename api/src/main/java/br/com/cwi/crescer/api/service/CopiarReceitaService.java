package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.domain.ReceitaAlimento;
import br.com.cwi.crescer.api.mapper.ReceitaMapper;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static br.com.cwi.crescer.api.domain.TipoConquista.COPIA_RECEITAS;

@Service
public class CopiarReceitaService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private BuscarReceitaService buscarReceitaService;
    @Autowired
    private ReceitaRepository receitaRepository;
    @Autowired
    private BuscarAlimentoService buscarAlimentoService;

    @Autowired
    private ConquistasService conquistasService;
    @Autowired
    private NowService nowService;

    @Transactional
    public void copiar(Long idReceita) {
        Usuario usuario = usuarioAutenticadoService.get();
        Receita original = buscarReceitaService.porId(idReceita);
        Receita copia = ReceitaMapper.copiar(original);
        copia.setAlimentos(
                original.getAlimentos().stream().map(
                        alimento -> {
                            ReceitaAlimento receitaAlimento = new ReceitaAlimento();
                            receitaAlimento.setReceita(copia);
                            receitaAlimento.setAlimento(buscarAlimentoService.porId(alimento.getAlimento().getId()));
                            receitaAlimento.setQuantidade(alimento.getQuantidade());
                            copia.adicionarAlimento(buscarAlimentoService.porId(alimento.getId()), receitaAlimento.getQuantidade());
                            return receitaAlimento;
                        }
                ).collect(Collectors.toList()));
        copia.setDataCriacao(nowService.nowDate());
        copia.setUsuario(usuario);

        usuario.getReceitas().add(copia);
        conquistasService.conquistasQuantidadeDeAcoes(original.getUsuario(), COPIA_RECEITAS);

        receitaRepository.save(copia);
    }
}
