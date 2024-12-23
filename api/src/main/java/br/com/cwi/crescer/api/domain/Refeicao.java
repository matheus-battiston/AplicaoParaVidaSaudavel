package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Refeicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDate dia;

    @Enumerated(value = STRING)
    private Periodo periodo;

    private Integer calorias;

    private Integer proteinas;

    private Integer carboidratos;

    private Integer lipidios;

    @OneToMany(mappedBy = "refeicao")
    private List<RefeicaoAlimento> alimentos = new ArrayList<>();

    public void adicionarAlimento(RefeicaoAlimento refeicaoAlimento) {
        refeicaoAlimento.setRefeicao(this);
        this.alimentos.add(refeicaoAlimento);
    }
    public void removerAlimento(RefeicaoAlimento refeicaoAlimento) {
        refeicaoAlimento.setRefeicao(null);
        this.alimentos.remove(refeicaoAlimento);
    }
}
