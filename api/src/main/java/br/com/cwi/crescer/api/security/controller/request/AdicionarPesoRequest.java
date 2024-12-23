package br.com.cwi.crescer.api.security.controller.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class AdicionarPesoRequest {

    @NotNull(message = "Deve ser preenchido uma data")
    private LocalDate data;

    @NotNull(message = "Deve ser preenchido um peso")
    private Double peso;
}
