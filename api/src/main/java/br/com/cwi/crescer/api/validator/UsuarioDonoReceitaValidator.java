package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class UsuarioDonoReceitaValidator {
    public static final String MENSAGEM_USUARIO_NAO_E_DONO = "É necessário ser dono da receita fazer esta ação";
    public void validar(Usuario donoReceita, Usuario usuarioAutenticado){
        if (!donoReceita.equals(usuarioAutenticado)){
            throw new ResponseStatusException(BAD_REQUEST, MENSAGEM_USUARIO_NAO_E_DONO);
        }
    }
}
