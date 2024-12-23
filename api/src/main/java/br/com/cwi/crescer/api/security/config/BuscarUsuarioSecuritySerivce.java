package br.com.cwi.crescer.api.security.config;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class BuscarUsuarioSecuritySerivce implements UserDetailsService {

    public static final String NAO_ENCONTRADO = "Email nao encontrado";
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return usuarioRepository.findByEmail(email)
                .map(UsuarioSecurity::new)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));
    }

    public Usuario porEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(UNPROCESSABLE_ENTITY,NAO_ENCONTRADO));
    }

    public Usuario porEmailEToken(String email, String token) {
        return usuarioRepository.findByEmailAndTokenSenha(email, token)
                .orElseThrow(() -> new ResponseStatusException(UNPROCESSABLE_ENTITY,NAO_ENCONTRADO));

    }
}
