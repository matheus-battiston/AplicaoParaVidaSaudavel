package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.response.UsuarioPublicoResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.mapper.UsuarioPublicoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PesquisarUsuarioPorNomeService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    public Page<UsuarioPublicoResponse> pesquisar(String nome, Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findByNomeContainsIgnoreCase(nome, pageable);

        return usuarios
                .map(usuario -> {
                    Long posicao = usuarioRepository.findPosicaoUsuario(usuario.getId());
                    UsuarioPublicoResponse response = UsuarioPublicoMapper.toResponse(usuario);
                    response.setPosicao(posicao);
                    return response;

                });
    }
}
