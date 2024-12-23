package br.com.cwi.crescer.api.security.controller.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MensagemResponse {
    private Long idRemetente;
    private String mensagem;
}
