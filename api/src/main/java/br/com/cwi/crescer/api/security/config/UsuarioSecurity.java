package br.com.cwi.crescer.api.security.config;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class UsuarioSecurity implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;


    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public UsuarioSecurity(Usuario usuario) {

        this.id = usuario.getId();
        this.username = usuario.getEmail();
        this.password = usuario.getSenha();

        this.accountNonExpired = usuario.isAtivo();
        this.accountNonLocked = usuario.isAtivo();
        this.credentialsNonExpired = usuario.isAtivo();
        this.enabled = usuario.isAtivo();


    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
