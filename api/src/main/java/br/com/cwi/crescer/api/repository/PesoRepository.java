package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Peso;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PesoRepository extends JpaRepository<Peso, Long> {


    Peso findFirstByUsuarioOrderByDataRegistroDesc(Usuario usuario);

    List<Peso> findAllByUsuarioOrderByDataRegistroDesc(Usuario usuario);
}
