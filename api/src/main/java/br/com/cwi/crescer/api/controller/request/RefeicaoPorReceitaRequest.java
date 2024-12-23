package br.com.cwi.crescer.api.controller.request;

import br.com.cwi.crescer.api.domain.Periodo;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RefeicaoPorReceitaRequest {
    @NotNull
    private Long idReceita;

    @NotNull
    private Periodo periodo;

    @NotNull
    private LocalDate dia;
}
