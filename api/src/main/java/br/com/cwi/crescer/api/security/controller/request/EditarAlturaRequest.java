package br.com.cwi.crescer.api.security.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EditarAlturaRequest {
    @NotNull(message = "Altura deve ser informada")
    private Integer altura;
}
