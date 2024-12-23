package br.com.cwi.crescer.api.security.controller.response;

import br.com.cwi.crescer.api.domain.Categoria;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioPublicoResponse {
    private String nome;
    private String pontos;

    private String foto;

    private String titulo;

    private Categoria categoriaTag;

    private List<Long> idSeguidores ;
    private List<Long> idSeguindo;

    private Long id;

    private Long posicao;
}
