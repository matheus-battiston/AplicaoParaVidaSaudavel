package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class AvaliacaoReceita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receita_id")
    private Receita receita;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private int nota;

}
