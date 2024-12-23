package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListarPessoaResponse {

    private Long id;
    private String nome;
    private String foto;
}
