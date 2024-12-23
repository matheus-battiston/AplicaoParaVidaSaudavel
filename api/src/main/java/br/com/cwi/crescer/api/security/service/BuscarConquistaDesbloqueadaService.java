package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.repository.ConquistaUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class BuscarConquistaDesbloqueadaService {
    public static final String NAO_EXISTE_ESTA_CONQUISTA_DESBLOQUADA = "Esta conquista nao existe ou nao foi desbloqueada";
    @Autowired
    private ConquistaUsuarioRepository conquistaUsuarioRepository;

    public ConquistaUsuario porIdConquista(Usuario usuario, Long idConquista) {
        return conquistaUsuarioRepository
                .findFirstByUsuarioAndDesbloqueadaIsTrueAndConquista_Id(usuario, idConquista)
                .orElseThrow(() -> new ResponseStatusException(UNPROCESSABLE_ENTITY, NAO_EXISTE_ESTA_CONQUISTA_DESBLOQUADA));
    }
}
