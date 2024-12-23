package br.com.cwi.crescer.api.controller.response;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

    private Long id;
    private Integer nroCurtidas;
    private Integer nroComentarios;
    private boolean curtido;
    private boolean privado;
    private String texto;
    private String imagem;
    private ListarPessoaResponse autor;
    private LocalDate dataInclusao;

}
