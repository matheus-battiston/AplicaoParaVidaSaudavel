package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.AvaliacaoReceita;
import br.com.cwi.crescer.api.domain.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoReceitaRepository extends JpaRepository<AvaliacaoReceita, Long> {
    void deleteAllByReceita(Receita receita);
}
