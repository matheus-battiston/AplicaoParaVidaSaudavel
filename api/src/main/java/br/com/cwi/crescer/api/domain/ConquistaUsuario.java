package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class ConquistaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "conquista_id")
    private Conquista conquista;

    private boolean desbloqueada;

    private Long progresso;

    private LocalDate dataAlteracao;


    public void adicionarProgresso() {
        this.progresso = this.progresso + 1;
        this.dataAlteracao = LocalDate.now();
    }

    public void resetarProgresso() {
        this.progresso = 1L;
        this.dataAlteracao = LocalDate.now();
    }
}
