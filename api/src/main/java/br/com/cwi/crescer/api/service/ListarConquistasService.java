package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ConquistasProgressoResponse;
import br.com.cwi.crescer.api.mapper.ConquistaMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarConquistasService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    public List<ConquistasProgressoResponse> listar() {
        Usuario usuario = usuarioAutenticadoService.get();

        return usuario.getConquistas()
                .stream()
                .map(ConquistaMapper::toResponse)
                .collect(Collectors.toList());

    }
}
