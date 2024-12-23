package br.com.cwi.crescer.api.security.service;


import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.DefinirFotoPerfilRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefinirFotoPerfilService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void definir(DefinirFotoPerfilRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();
        usuario.setImagemPerfil(request.getUrlFoto());

        usuarioRepository.save(usuario);
    }
}
