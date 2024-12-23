package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.domain.TipoConquista;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConquistaUsuarioRepository extends JpaRepository<ConquistaUsuario, Long> {
    List<ConquistaUsuario> findAllByUsuarioAndDesbloqueadaIsTrue(Usuario usuario);
    List<ConquistaUsuario> findAllByUsuarioAndConquistaTipo(Usuario usuario, TipoConquista tipo);

    Long countDistinctByUsuarioAndDesbloqueadaIsTrue(Usuario usuario);

    ConquistaUsuario findFirstByUsuarioAndConquistaTipo(Usuario usuario, TipoConquista conquista);

    Optional<ConquistaUsuario> findFirstByUsuarioAndDesbloqueadaIsTrueAndConquista_Id (Usuario usuario, Long idConquista);
}
