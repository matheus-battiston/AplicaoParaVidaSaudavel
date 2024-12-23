package br.com.cwi.crescer.api.controller.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class IdAlimentoRequest {
    private Long idAlimento;
}
