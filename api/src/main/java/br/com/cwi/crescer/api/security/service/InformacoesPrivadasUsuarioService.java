package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.response.InformacoesPrivadasUsuarioResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.mapper.InformacoesPrivadasUsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformacoesPrivadasUsuarioService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    public InformacoesPrivadasUsuarioResponse informar() {
        Usuario usuario = usuarioAutenticadoService.get();
        InformacoesPrivadasUsuarioResponse response =  InformacoesPrivadasUsuarioMapper.toResponse(usuario);
        Long posicao = usuarioRepository.findPosicaoUsuario(usuario.getId());
        response.setPosicao(posicao);
        return response;
    }
}
