package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversaRepository extends JpaRepository<Conversa, Long> {
    boolean existsByPrimeiroUsuarioAndSegundoUsuario(Usuario primeiroUsuario, Usuario segundoUsuario);
    boolean existsBySegundoUsuarioAndPrimeiroUsuario(Usuario segundoUsuario, Usuario primeiroUsuario);
    List<Conversa> findAllByPrimeiroUsuarioOrSegundoUsuario(Usuario primeiroUsuario, Usuario segundoUsuario);
    Optional<Conversa> findByPrimeiroUsuarioEqualsAndSegundoUsuarioEquals(Usuario primeiroUsuario, Usuario segundoUsuario);

}
