package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Peso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String valor;
    private LocalDate dataRegistro;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
