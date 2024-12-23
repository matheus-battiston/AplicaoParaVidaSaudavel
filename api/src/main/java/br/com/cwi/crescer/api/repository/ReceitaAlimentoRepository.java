package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.ReceitaAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReceitaAlimentoRepository extends JpaRepository<ReceitaAlimento, Long> {
    @Modifying
    @Query(value = "delete from ReceitaAlimento ra where ra.receita.id = ?1")
    void deleteAllByReceita_Id(Long receita_id);
    @Query (value = "select distinct r from ReceitaAlimento r where lower(r.alimento.nome) like CONCAT('%',?1,'%')")
    List<ReceitaAlimento> findByAlimentoNome(String nome);
}
