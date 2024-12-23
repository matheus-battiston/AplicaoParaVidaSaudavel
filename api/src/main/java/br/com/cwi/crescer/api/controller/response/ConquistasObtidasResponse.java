package br.com.cwi.crescer.api.controller.response;

import br.com.cwi.crescer.api.domain.Categoria;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConquistasObtidasResponse {
    private String descricao;
    private LocalDate dataConquista;
    private Categoria categoria;
}
