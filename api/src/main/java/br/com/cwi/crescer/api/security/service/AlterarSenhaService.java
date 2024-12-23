package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.config.BuscarUsuarioSecuritySerivce;
import br.com.cwi.crescer.api.security.controller.request.AlterarSenhaRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlterarSenhaService {
    @Autowired
    private BuscarUsuarioSecuritySerivce buscarUsuarioSecuritySerivce;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void alterar(AlterarSenhaRequest request) {
        Usuario usuario = buscarUsuarioSecuritySerivce.porEmailEToken(request.getEmail(), request.getToken());
        usuario.setTokenSenha(null);
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));

        usuarioRepository.save(usuario);
    }
}
