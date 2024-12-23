package br.com.cwi.crescer.api.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ReceitaRequest {

    @NotBlank(message = "Deve ser informado um modo de preparo")
    @Size(max = 511, message = "Deve ter no maximo 511 caracteres")
    private String modoPreparo;

    private String imagem;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotNull
    private Boolean privado;

    @NotNull
    private Boolean copia;

    @Valid @NotNull
    private List<ReceitaAlimentoRequest> alimentos;









}
