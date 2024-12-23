package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlimentoResponse {
    private Long id;
    private String nome;
    private String calorias;
    private String proteinas;
    private String carboidratos;
    private String lipidios;
    private boolean comunidade;
    private String quantidade;
}
