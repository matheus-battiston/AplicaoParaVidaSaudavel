package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;

@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String nota;
    private String modoPreparo;
    private String imagem;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "criador_id")
    private Usuario criador;

    @OneToMany(mappedBy = "receita", cascade = PERSIST)
    private List<ReceitaAlimento> alimentos = new ArrayList<>();

    @OneToMany(mappedBy = "receita", cascade = PERSIST)
    private List<AvaliacaoReceita> avaliacoes = new ArrayList<>();


    public void adicionarAlimento(Alimento alimento, String quantidade) {
        ReceitaAlimento receitaAlimento = new ReceitaAlimento();
        receitaAlimento.setQuantidade(quantidade);
        receitaAlimento.setAlimento(alimento);
        receitaAlimento.setReceita(this);
        this.alimentos.add(receitaAlimento);
    }



    public void adicionarNota(AvaliacaoReceita avaliacaoReceita) {
        this.avaliacoes.add(avaliacaoReceita);
        avaliacaoReceita.setReceita(this);
    }
}
