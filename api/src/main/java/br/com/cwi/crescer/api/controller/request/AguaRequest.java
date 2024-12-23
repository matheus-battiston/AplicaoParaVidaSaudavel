package br.com.cwi.crescer.api.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class AguaRequest {
    @NotNull
    private int quantidade;

    @NotNull
    private LocalDate data;
}
