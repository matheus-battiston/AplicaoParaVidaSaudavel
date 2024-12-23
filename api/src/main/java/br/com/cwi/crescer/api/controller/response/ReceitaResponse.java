package br.com.cwi.crescer.api.controller.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReceitaResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;
    private boolean privado;
    private boolean copia;
    private Integer calorias;
    private Integer proteinas;
    private Integer carboidratos;
    private Integer lipidios;
    private List<AlimentoResponse> alimentos;
    private Long usuarioId;
    private Long criadorId;
    private String criadorNome;
    private String nota;
    private boolean avaliado;
    private String modoPreparo;
    private String imagem;

}
