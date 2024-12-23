package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findByNomeContainsIgnoreCase(String nome, Pageable pageable);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailAndTokenSenha(String email, String token);
    List<Usuario> findUsuarioByNomeContainingIgnoreCase(String pesquisa);
    @Query(value = "select u from Usuario u order by cast(u.pontuacao as integer) desc ")
    Page<Usuario> findRanking(Pageable pageable);

    @Query(value = "SELECT COUNT(*) + 1 AS position FROM usuario WHERE pontuacao > ( SELECT pontuacao FROM usuario WHERE id = 1 )", nativeQuery = true)
    Long findPosicaoUsuario(Long usuarioId);


}
