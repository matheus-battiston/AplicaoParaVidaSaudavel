package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.ComentarioPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<ComentarioPost, Long> {

}
