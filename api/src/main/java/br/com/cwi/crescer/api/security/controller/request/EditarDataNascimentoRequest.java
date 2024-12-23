package br.com.cwi.crescer.api.security.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class EditarDataNascimentoRequest {

    @NotNull(message = "Deve ser informado uma data")
    private LocalDate data;
}
