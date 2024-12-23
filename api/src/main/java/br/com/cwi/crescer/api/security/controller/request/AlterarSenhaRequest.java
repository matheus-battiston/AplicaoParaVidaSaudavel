package br.com.cwi.crescer.api.security.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AlterarSenhaRequest {
    @NotBlank(message = "Deve ser fornecido o token")
    private String token;

    @NotBlank(message = "Deve ser informado o email")
    private String email;

    @NotBlank(message = "Deve ser informada a nova senha")
    private String senha;
}
