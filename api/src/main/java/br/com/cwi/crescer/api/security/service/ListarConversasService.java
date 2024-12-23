package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.repository.ConversaRepository;
import br.com.cwi.crescer.api.security.controller.response.ConversaResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.cwi.crescer.api.security.mapper.ConversaMapper.toResponse;

@Service
public class ListarConversasService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private ConversaRepository conversaRepository;

    public List<ConversaResponse> listar(){
        Usuario usuario = usuarioAutenticadoService.get();
        List<Conversa> conversas = conversaRepository.findAllByPrimeiroUsuarioOrSegundoUsuario(usuario, usuario);
        return conversas.stream()
                .filter(conversa -> !(conversa.getPrimeiroUsuario().equals(usuario) && conversa.getSegundoUsuario().equals(usuario)))
                .map(conversa -> {
                    if(conversa.getPrimeiroUsuario().equals(usuario)){
                        return toResponse(conversa.getSegundoUsuario(), conversa);
                    } return toResponse(conversa.getPrimeiroUsuario(), conversa);
                })
                .collect(Collectors.toList());
    }

}
