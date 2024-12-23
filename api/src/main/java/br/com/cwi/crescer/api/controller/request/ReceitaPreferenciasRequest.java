package br.com.cwi.crescer.api.controller.request;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ReceitaPreferenciasRequest {
    private List<String> alergias;

}
