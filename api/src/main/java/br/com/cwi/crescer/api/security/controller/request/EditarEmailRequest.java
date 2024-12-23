package br.com.cwi.crescer.api.security.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EditarEmailRequest {

    @NotBlank(message = "Deve ser informado o email")
    @Email(message = "Deve ser informado um email valido")
    @Size(max = 255, message = "Email deve ter no maximo 255 caracteres")
    private String email;
}
