package br.com.cwi.crescer.api.security.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class DefinirFotoPerfilRequest {
    @NotBlank(message = "Deve ser fornecido uma url de foto")
    private String urlFoto;
}
