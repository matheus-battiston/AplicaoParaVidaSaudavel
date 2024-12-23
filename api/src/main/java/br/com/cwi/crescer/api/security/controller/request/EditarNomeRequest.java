package br.com.cwi.crescer.api.security.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
public class EditarNomeRequest {
    @NotBlank(message = "O novo nome deve ser preenchido")
    @Size(max = 255, message = "Nome pode ter no maximo 255 caracteres")
    private String nome;
}
