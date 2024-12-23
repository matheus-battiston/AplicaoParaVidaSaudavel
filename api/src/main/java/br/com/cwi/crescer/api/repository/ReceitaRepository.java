package br.com.cwi.crescer.api.repository;


import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.domain.ReceitaAlimento;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    @Query(value = "select r from Receita r inner join Usuario u on u.id=r.usuario.id" +
            " where r.id=?1" +
            " and (r.privado=false or r.usuario=?2 or ?2 member of r.usuario.seguindo)")
    Optional<Receita> findAllowedById(Long id, Usuario logado);

    boolean existsByUsuarioAndDataCriacao(Usuario usuario, LocalDate dataCriacao);

    @Query("select distinct r from Receita r where ((r.usuario IN :seguindo) AND (r.privado = false OR :usuario MEMBER OF r.usuario.seguindo)) ORDER BY r.dataCriacao DESC ")
    Page<Receita> findReceitasSeguindo(@Param("seguindo") List<Usuario> seguindo,@Param("usuario") Usuario usuario ,Pageable pageable);

    @Query(value = "select r from Receita r inner join Usuario u on u.id=r.usuario.id" +
            " where r.usuario.id=?1" +
            " and (r.privado=false or r.usuario=?2 or ?2 member of r.usuario.seguindo)")
    Page<Receita> findAllowedByUsuarioId(Long usuario_id, Usuario logado, Pageable pageable);

    Page<Receita> findByUsuario_Id(Long usuario_id, Pageable pageable);

    Page<Receita> findByPrivadoIsFalseOrderByNotaDesc(Pageable pageable);

    @Query(value = "select r from Receita r where not exists (select 1 from r.alimentos a where a in ?1) and r.privado=false order by r.nota desc ")
    Page<Receita> findTopPorAlergias(List<ReceitaAlimento> alimentos, Pageable pageable);

    @Query(value = "select r from Receita r where r.privado=false and (cast(r.calorias as double ) between ?1*0.7 and ?1*1.3) and (cast(r.proteinas as double ) between ?2*0.7 and ?2*1.3) and not r.id=?3")
    List<Receita> findSugestions(double calorias, double proteinas, Long id);

}
