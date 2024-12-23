package br.com.cwi.crescer.api.repository;


import br.com.cwi.crescer.api.domain.Alimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    Page<Alimento> findByNomeContainsIgnoreCase(String nome, Pageable pageable);
    List<Alimento> findByNomeContainsIgnoreCase(String nome);

}
