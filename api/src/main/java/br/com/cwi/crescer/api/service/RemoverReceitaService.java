package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.repository.AvaliacaoReceitaRepository;
import br.com.cwi.crescer.api.repository.ReceitaAlimentoRepository;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.UsuarioDonoReceitaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoverReceitaService {
    @Autowired
    private BuscarReceitaService buscarReceitaService;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private ReceitaAlimentoRepository receitaAlimentoRepository;

    @Autowired
    private AvaliacaoReceitaRepository avaliacaoReceitaRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioDonoReceitaValidator usuarioDonoReceitaValidator;

    @Transactional
    public void remover(Long id){
        Receita receita = buscarReceitaService.porId(id);
        Usuario usuarioAutenticado = usuarioAutenticadoService.get();
        Usuario usuario = receita.getUsuario();

        usuarioDonoReceitaValidator.validar(usuario, usuarioAutenticado);

        usuario.removerReceita(receita);
        receitaAlimentoRepository.deleteAllByReceita_Id(receita.getId());
        avaliacaoReceitaRepository.deleteAllByReceita(receita);
        receitaRepository.delete(receita);
    }
}
