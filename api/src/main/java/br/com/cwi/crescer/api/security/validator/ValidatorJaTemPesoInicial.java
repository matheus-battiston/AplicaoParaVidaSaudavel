package br.com.cwi.crescer.api.security.validator;

import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class ValidatorJaTemPesoInicial {
    public static final String JA_TEM = "Usuario ja tem peso definido";
    public void confirmaQueNaoTem(Usuario usuario) {
        if (Objects.nonNull(usuario.getPeso())){
            throw new ResponseStatusException(BAD_REQUEST, JA_TEM);
        }
    }
}
