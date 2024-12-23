package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.response.UsuarioPublicoResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.BuscarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.crescer.api.security.mapper.UsuarioPublicoMapper.toResponse;

@Service
public class ExibirInformacoesPublicasService {

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioPublicoResponse exibir(Long idUsuario) {
        Usuario procurado = buscarUsuarioService.porId(idUsuario);
        Long posicao = usuarioRepository.findPosicaoUsuario(procurado.getId());
        UsuarioPublicoResponse response = toResponse(procurado);
        response.setPosicao(posicao);
        return response;
    }
}
