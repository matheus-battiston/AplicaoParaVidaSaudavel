package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.RefeicaoAlimento;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RefeicaoAlimentoRepository extends JpaRepository<RefeicaoAlimento, Long> {
    Optional<RefeicaoAlimento> findByAlimento_IdAndRefeicao_Id(Long alimento_id, Long refeicao_id);
    @Query("select ra " +
            "from RefeicaoAlimento ra " +
            "join ra.refeicao refeicao " +
            "where refeicao.usuario = :usuario " +
            "order by refeicao.dia desc")
        List<RefeicaoAlimento> findAllRecentlyUsed(Usuario usuario);
}
