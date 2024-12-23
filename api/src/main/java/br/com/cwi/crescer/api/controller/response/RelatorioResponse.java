package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RelatorioResponse {
    private Integer calorias;
    private Integer proteinas;
    private Integer carboidratos;
    private Integer lipidios;
    private Integer agua;
    private Integer metaAgua;
    private Integer metaCalorias;
    private Integer metaProteinas;
    private Integer metaCarboidratos;
    private Integer metaLipidios;
}
