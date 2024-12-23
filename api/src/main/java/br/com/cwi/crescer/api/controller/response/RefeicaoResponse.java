package br.com.cwi.crescer.api.controller.response;

import br.com.cwi.crescer.api.domain.Periodo;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefeicaoResponse {
    private Long id;
    private Integer carboidratos;
    private Integer proteinas;
    private Integer lipidios;
    private Integer calorias;
    private Periodo periodo;
    private LocalDate dia;
    private List<AlimentoResponse> alimentos;
}
