package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ConquistasObtidasResponse;
import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.mapper.ConquistaMapper;
import br.com.cwi.crescer.api.repository.ConquistaUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarConquistasObtidasService {
    @Autowired
    private ConquistaUsuarioRepository conquistaUsuarioRepository;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    public List<ConquistasObtidasResponse> listar(Long idUsuario) {
        Usuario usuario = buscarUsuarioService.porId(idUsuario);
        List<ConquistaUsuario> conquistas = conquistaUsuarioRepository.findAllByUsuarioAndDesbloqueadaIsTrue(usuario);

        return conquistas.stream()
                .map(ConquistaMapper::toResponseConquistado)
                .collect(Collectors.toList());
    }
}
