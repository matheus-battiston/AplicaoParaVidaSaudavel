package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.TagsResponse;
import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.mapper.TagsResponseMapper;
import br.com.cwi.crescer.api.repository.ConquistaUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarTagsDisponiveisService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private ConquistaUsuarioRepository conquistaUsuarioRepository;

    public List<TagsResponse> listar() {
        Usuario usuario = usuarioAutenticadoService.get();
        List<ConquistaUsuario> conquistasObtidas = conquistaUsuarioRepository
                .findAllByUsuarioAndDesbloqueadaIsTrue(usuario);

        return conquistasObtidas.stream()
                .map(TagsResponseMapper::toResponse)
                .collect(Collectors.toList());
    }
}
