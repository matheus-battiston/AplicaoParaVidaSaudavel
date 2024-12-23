package br.com.cwi.crescer.api.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Conquista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long valor;

    private String descricao;

    private String recompensa;

    @Enumerated(value = STRING)
    private Categoria categoria;

    @Enumerated(value = STRING)
    private TipoConquista tipo;
}
