package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
public class ValidarEmailUnicoService {
    public static final String EMAIL_JA_EXISTE = "Email ja cadastrado";
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validar(String email) {
        if (usuarioRepository.findAll().stream().map(Usuario::getEmail).collect(Collectors.toList()).contains(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EMAIL_JA_EXISTE);
        }
    }
}
