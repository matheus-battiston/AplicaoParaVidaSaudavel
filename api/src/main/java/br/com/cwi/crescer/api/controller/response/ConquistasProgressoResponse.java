package br.com.cwi.crescer.api.controller.response;

import br.com.cwi.crescer.api.domain.Categoria;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConquistasProgressoResponse {
    private Long id;
    private Long progresso;
    private Long objetivo;
    private String descricao;
    private Categoria categoria;
    private boolean desbloqueada;
    private String recompensa;

    private Long idConquista;
}
