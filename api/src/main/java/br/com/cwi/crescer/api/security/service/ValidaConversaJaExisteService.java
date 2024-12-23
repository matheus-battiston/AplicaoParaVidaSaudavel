package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.repository.ConversaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ValidaConversaJaExisteService {
    @Autowired
    private ConversaRepository conversaRepository;

    public static final String MENSAGEM_CONVERSA_JA_EXISTE = "Esta conversa já existe";

    public void validar(Usuario usuarioAutenticado, Usuario usuarioAAdicionar){
        Optional<Conversa> conversa = conversaRepository
                .findAllByPrimeiroUsuarioOrSegundoUsuario(usuarioAutenticado, usuarioAutenticado).stream()
                .filter(conversaUsuario -> conversaUsuario.getPrimeiroUsuario().equals(usuarioAAdicionar)
                        || conversaUsuario.getSegundoUsuario().equals(usuarioAAdicionar))
                .findFirst();

        if(conversa.isPresent()){
            throw new ResponseStatusException(BAD_REQUEST, MENSAGEM_CONVERSA_JA_EXISTE);
        }


    }
}
