package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComentarioResponse {

    private Long id;
    private ListarPessoaResponse usuario;
    private String comentario;
}
