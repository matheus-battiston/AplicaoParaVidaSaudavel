package br.com.cwi.crescer.api.security.controller.request;

import br.com.cwi.crescer.api.security.domain.Meta;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DefinirMetaRequest {
    @NotNull(message = "Deve ser fornecido uma meta")
    private Meta meta;
}
