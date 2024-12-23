package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private String foto;
    private boolean informado;

}
