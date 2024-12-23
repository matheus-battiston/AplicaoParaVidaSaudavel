package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String texto;

    private LocalDateTime dataInclusao;

    private String imagem;

    private boolean privado;


    @ManyToMany(mappedBy = "postsCurtidos")
    private List<Usuario> usuariosCurtiram = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<ComentarioPost> comentariosPost = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<CurtidaPost> curtidasPost = new ArrayList<>();

    public void adicionarComentario(ComentarioPost comentarioPost) {
        this.comentariosPost.add(comentarioPost);
        comentarioPost.setPost(this);
    }

    public void adicionarCurtida(CurtidaPost curtidaPost) {
        this.curtidasPost.add(curtidaPost);
        curtidaPost.setPost(this);
    }

    public void adicionarCurtidaUsuario(Usuario usuario) {
        this.usuariosCurtiram.add(usuario);
    }

    public void removerUsuariosCurtiram(Usuario usuario) {
        this.usuariosCurtiram.remove(usuario);
    }

    public void limpeza() {
        this.getComentariosPost().forEach(ComentarioPost::removerComentario);
        this.getUsuariosCurtiram().forEach(usuarioCurtiu -> usuarioCurtiu.getPostsCurtidos().remove(this));
        this.usuario.removerPost(this);
    }

}
