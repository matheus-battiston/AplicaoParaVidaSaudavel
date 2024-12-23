package br.com.cwi.crescer.api.controller.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NovoPostRequest {
    @NotBlank(message = "Deve conter um texto")
    @Size(max = 511, message = "Texto pode ter no maximo 511 caracteres")
    private String texto;

    @Size(max = 511, message = "URL deve ter no maximo 511 caracteres")
    private String url;

    @NotNull(message = "Deve ser fornecido um tipo de permissao")
    private boolean privado;
}
