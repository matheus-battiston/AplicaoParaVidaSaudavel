package br.com.cwi.crescer.api.security.controller.response;

import br.com.cwi.crescer.api.domain.Categoria;
import br.com.cwi.crescer.api.security.domain.AtividadeFisica;
import br.com.cwi.crescer.api.security.domain.Meta;
import br.com.cwi.crescer.api.security.domain.Sexo;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InformacoesPrivadasUsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private Integer altura;
    private String peso;
    private Sexo sexo;
    private String imagemPerfil;
    private String imc;
    private Integer gastoCalorico;
    private Integer aguaRecomendacao;
    private Integer caloriasRecomendacao;
    private Integer carboidratosRecomendacao;
    private Integer lipidiosRecomendacao;
    private Integer proteinasRecomendacao;
    private Meta meta;
    private AtividadeFisica atividadeFisica;
    private Integer nroSeguidores;
    private String pontuacao;
    private Long posicao;
    private Categoria categoriaTag;



}
