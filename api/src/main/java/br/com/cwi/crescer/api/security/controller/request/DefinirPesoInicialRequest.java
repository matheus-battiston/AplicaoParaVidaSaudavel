package br.com.cwi.crescer.api.security.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DefinirPesoInicialRequest {

    @NotNull(message = "Deve ser informado um peso")
    private Double pesoInicial;
}
