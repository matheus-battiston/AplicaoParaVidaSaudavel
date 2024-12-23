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
public class ComentarioPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String texto;

    public void removerComentario() {
        this.usuario.getComentariosPost().remove(this);
    }
}
