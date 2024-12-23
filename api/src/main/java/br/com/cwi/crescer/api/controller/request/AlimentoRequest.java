package br.com.cwi.crescer.api.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AlimentoRequest {
    @NotBlank
    private String nome;

    @NotBlank
    private String calorias;

    @NotBlank
    private String proteinas;

    @NotBlank
    private String carboidratos;

    @NotBlank
    private String lipidios;
}
