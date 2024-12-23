package br.com.cwi.crescer.api.controller.response;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PessoaRankingResponse {
    private String nome;
    private String foto;
    private Long id;
    private String pontos;
}
