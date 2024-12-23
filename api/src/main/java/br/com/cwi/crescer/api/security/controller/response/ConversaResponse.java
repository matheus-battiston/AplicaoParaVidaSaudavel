package br.com.cwi.crescer.api.security.controller.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConversaResponse {
    private Long idConversa;
    private Long idUsuario;
    private String nome;
    private String foto;
}
