package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Periodo;
import br.com.cwi.crescer.api.domain.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
    Optional<Refeicao> findByPeriodoEqualsAndDiaEquals(Periodo periodo, LocalDate dia);
    boolean existsByDia(LocalDate dia);

    List<Refeicao> findByDiaAndUsuario_Id(LocalDate dia, Long usuario_id);
}
