package br.com.cwi.crescer.api.security.controller.request;

import br.com.cwi.crescer.api.security.domain.Sexo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class DefinirGeneroRequest {

    @NotBlank
    private Sexo sexo;
}
