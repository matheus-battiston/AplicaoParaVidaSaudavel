package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT DISTINCT p FROM Post p WHERE p.id = :idPost AND (p.usuario = :usuario OR p.privado = false or :usuario MEMBER OF p.usuario.seguindo)")
    Optional<Post> findAllowedById(@Param("idPost") Long idPost, @Param("usuario") Usuario usuario);

    @Query("select distinct p from Post p where ((p.usuario IN :seguindo OR p.usuario = :usuario) AND (p.privado = false OR :usuario MEMBER OF p.usuario.seguindo OR p.usuario = :usuario))")
    Page<Post> findTodosPostsSeguindo(List<Usuario> seguindo, Usuario usuario, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p WHERE ((p.usuario.id = :idUsuario) AND ((p.privado = false) OR ((:usuarioAutenticado MEMBER OF p.usuario.seguindo) OR (p.usuario= :usuarioAutenticado))))")
    Page<Post> findPostsDoUsuarioPermitido(Long idUsuario, Usuario usuarioAutenticado, Pageable pageable);
}
