package br.com.cwi.crescer.api.security.controller.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioPorNomeResponse {
    private String nome;
    private String foto;
    private Long id;
    private Long position;
}
