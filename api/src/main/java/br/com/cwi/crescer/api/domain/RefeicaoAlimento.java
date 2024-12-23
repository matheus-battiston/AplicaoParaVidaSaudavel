package br.com.cwi.crescer.api.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.PERSIST;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class RefeicaoAlimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "refeicao_id")
    private Refeicao refeicao;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "alimento_id")
    private Alimento alimento;

    private String quantidade;
}
