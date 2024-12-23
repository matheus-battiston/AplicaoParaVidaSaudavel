package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
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
public class Conversa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "primeiro_usuario_id")
    private Usuario primeiroUsuario;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "segundo_usuario_id")
    private Usuario segundoUsuario;

}
