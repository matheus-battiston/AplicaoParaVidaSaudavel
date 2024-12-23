package br.com.cwi.crescer.api.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RefeicaoAlimentoRequest {
    @NotBlank
    private Long id;
    @NotBlank
    private String quantidade;
}
