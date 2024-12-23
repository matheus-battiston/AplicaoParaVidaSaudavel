package br.com.cwi.crescer.api.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class AvaliacaoReceitaRequest {
    @NotNull
    String nota;
}
