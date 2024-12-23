package br.com.cwi.crescer.api.security.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EsqueceuSenhaRequest {
    @NotBlank(message = "Deve ser fornecido um email")
    @Email(message = "Deve ser fornecido um email valido")
    private String email;
}
