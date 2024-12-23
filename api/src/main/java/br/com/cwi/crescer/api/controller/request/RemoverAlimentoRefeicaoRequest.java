package br.com.cwi.crescer.api.controller.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RemoverAlimentoRefeicaoRequest {
    @NotNull
    private Long idAlimento;
}
