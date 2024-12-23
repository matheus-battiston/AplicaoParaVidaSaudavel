package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class BuscarUsuarioService {
    public static final String NAO_ENCONTRADO = "Usuário não encontrado";
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public Usuario porId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(UNPROCESSABLE_ENTITY,NAO_ENCONTRADO));
    }

    public Usuario logado() {
        return usuarioAutenticadoService.get();
    }

}
