package br.com.cwi.crescer.api.security.controller.request;

import br.com.cwi.crescer.api.security.domain.AtividadeFisica;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DefinirAtivdadeRequest {
    @NotNull(message = "Deve ser fornecido um comportamento")
    private AtividadeFisica atividadeFisica;
}
