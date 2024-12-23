package br.com.cwi.crescer.api.controller.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EditarPrivacidadeRequest {
    @NotNull(message = "Deve ser informado uma nova privacidade")
    private boolean novaPrivacidade;
}
