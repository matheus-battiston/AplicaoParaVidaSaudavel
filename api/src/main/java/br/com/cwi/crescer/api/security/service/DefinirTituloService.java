package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefinirTituloService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BuscarConquistaDesbloqueadaService buscarConquistaDesbloqueadaService;

    @Transactional
    public void definir(Long idConquista) {
        Usuario usuario = usuarioAutenticadoService.get();
        ConquistaUsuario conquista = buscarConquistaDesbloqueadaService.porIdConquista(usuario, idConquista);
        usuario.setTitulo(conquista.getConquista().getRecompensa());
        usuario.setCategoriaTitulo(conquista.getConquista().getCategoria());
        usuarioRepository.save(usuario);
    }
}
