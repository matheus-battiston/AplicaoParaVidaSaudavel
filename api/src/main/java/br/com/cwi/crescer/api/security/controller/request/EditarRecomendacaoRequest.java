package br.com.cwi.crescer.api.security.controller.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditarRecomendacaoRequest {
    @NotNull
    private Integer valor;
}
