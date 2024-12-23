package br.com.cwi.crescer.api.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter @AllArgsConstructor@NoArgsConstructor
public class ReceitaAlimentoRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String quantidade;
}
