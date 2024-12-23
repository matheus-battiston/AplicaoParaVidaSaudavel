package br.com.cwi.crescer.api.security.validator;

import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class    ValidatorJaSegue {
    public static final String JA_SEGUE = "Não é possível seguir um usuario ja seguido";
    public static final String NAO_SEGUE = "Não é possível deixar de seguir um usuario não seguido";
    public void validarNaoSegue(Usuario usuarioAutenticado, Usuario seguido) {
        if(usuarioAutenticado.getSeguindo().contains(seguido)){
            throw new ResponseStatusException(BAD_REQUEST, JA_SEGUE);
        }
    }

    public void validarJaSegue(Usuario usuarioAutenticado, Usuario seguido) {
        if(!usuarioAutenticado.getSeguindo().contains(seguido)){
            throw new ResponseStatusException(BAD_REQUEST, NAO_SEGUE);
        }
    }
}
