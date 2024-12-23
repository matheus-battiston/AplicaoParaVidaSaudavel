package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Agua;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AguaRepository extends JpaRepository<Agua, Long> {
    Optional<Agua> findByUsuario_IdAndDataRegistro(Long usuario_id, LocalDate dataRegistro);

}
