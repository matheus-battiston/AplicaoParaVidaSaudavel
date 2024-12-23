package br.com.cwi.crescer.api.security.controller.request;

import br.com.cwi.crescer.api.security.domain.AtividadeFisica;
import br.com.cwi.crescer.api.security.domain.Meta;
import br.com.cwi.crescer.api.security.domain.Sexo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class InformacoesIniciaisRequest {
    @NotNull @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @NotNull(message = "Deve ser informado um peso")
    private String peso;
    @NotNull(message = "Altura deve ser informada")
    private Integer altura;
    @NotNull(message = "Deve ser fornecido uma meta") @Enumerated(EnumType.STRING)
    private Meta meta;
    @NotNull(message = "Deve ser fornecido um comportamento") @Enumerated(EnumType.STRING)
    private AtividadeFisica atividadeFisica;
    private String imagem;

}
