package br.com.cwi.crescer.api.controller.response;


import br.com.cwi.crescer.api.domain.Categoria;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TagsResponse {
    private String tag;
    private Categoria categoria;
    private Long idConquista;
}
