package br.com.cwi.crescer.api.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NovoComentarioRequest {
    @NotBlank(message = "Texto do comentario deve ser preenchido")
    @Size(max = 255, message = "Texto deve ter no maximo 255 caracteres")
    private String texto;

}
