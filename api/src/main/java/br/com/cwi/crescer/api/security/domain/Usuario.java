package br.com.cwi.crescer.api.security.domain;

import br.com.cwi.crescer.api.domain.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Double.parseDouble;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Getter @Setter @EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Usuario {
    public static final Integer ML_DE_AGUA_POR_KG = 35;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private Integer altura;
    private String peso;

    @Enumerated(value = STRING)
    private Sexo sexo;
    private String imagemPerfil;
    private String pontuacao;
    private String titulo;
    private String imc;
    private Integer gastoCalorico;
    private Integer aguaRecomendacao;
    private Integer caloriasRecomendacao;
    private Integer proteinasRecomendacao;
    private Integer carboidratosRecomendacao;
    private Integer lipidiosRecomendacao;
    private boolean ativo;
    private String tokenSenha;

    @Enumerated(value = STRING)
    private Categoria categoriaTitulo;

    @Enumerated(value = STRING)
    private Meta meta;

    @Enumerated(value = STRING)
    private AtividadeFisica atividadeFisica;

    @OneToMany(mappedBy = "usuario", cascade = ALL)
    private List<Peso> pesos = new ArrayList<>();
    @OneToMany(mappedBy = "usuario")
    private List<Refeicao> refeicoes = new ArrayList<>();
    @OneToMany(mappedBy = "usuario")
    private List<Receita> receitas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Agua> aguas = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "amizade",
            joinColumns = @JoinColumn(name = "seguido"),
            inverseJoinColumns = @JoinColumn(name = "seguidor"))
    private List<Usuario> seguidores = new ArrayList<>();

    @ManyToMany(mappedBy = "seguidores")
    private List<Usuario> seguindo = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<AvaliacaoReceita> avaliacoesReceitas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<ComentarioPost> comentariosPost = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<CurtidaPost> curtidasPost = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "curtida_post",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> postsCurtidos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario"  ,cascade = PERSIST)
    private List<ConquistaUsuario> conquistas = new ArrayList<>();


    public void adicionarPeso(Peso peso) {
        this.pesos.add(peso);
        peso.setUsuario(this);
    }

    public void removerPeso(Peso peso) {
        this.pesos.remove(peso);
        peso.setUsuario(null);
    }
    public void adicionarRefeicao(Refeicao refeicao) {
        this.refeicoes.add(refeicao);
        refeicao.setUsuario(this);
    }

    public void removerRefeicao(Refeicao refeicao) {
        this.refeicoes.remove(refeicao);
        refeicao.setUsuario(null);
    }

    public void seguirUsuario(Usuario seguindo) {
        this.seguindo.add(seguindo);
        seguindo.getSeguidores().add(this);
    }


    public void adicionarAgua(Agua agua) {
        this.aguas.add(agua);
        agua.setUsuario(this);
    }

    public void adicionarAvaliacao(AvaliacaoReceita avaliacaoReceita) {
        this.avaliacoesReceitas.add(avaliacaoReceita);
        avaliacaoReceita.setUsuario(this);
    }

    public void adicionarPost(Post post) {
        this.posts.add(post);
        post.setUsuario(this);
    }

    public void adicionarComentario(ComentarioPost comentarioPost) {
        this.comentariosPost.add(comentarioPost);
        comentarioPost.setUsuario(this);
    }

    public void adicionarCurtida(Post postCurtido) {
        this.postsCurtidos.add(postCurtido);
        postCurtido.adicionarCurtidaUsuario(this);

    }

    public void adicionarPontos(Long pontos){
        Long pontosAtuais = Long.valueOf(this.pontuacao);
        String novaPontuacao = String.valueOf(pontos + pontosAtuais);
        this.setPontuacao(novaPontuacao);
    }

    public void removerCurtidaPost(Post post) {
        this.postsCurtidos.remove(post);
        post.removerUsuariosCurtiram(this);
    }

    public void removerPost(Post post) {
        this.getPosts().remove(post);
    }

    public void adicionarReceita(Receita receita) {
        this.receitas.add(receita);
        receita.setUsuario(this);
    }

    public void removerReceita(Receita receita) {
        this.receitas.remove(receita);
        receita.setUsuario(null);
    }

    public void alterarPesoAtual(Peso peso){
        Double valorPeso = Double.parseDouble(peso.getValor());;
        this.setPeso(peso.getValor());
        long novaQuantidadeDeAgua = ML_DE_AGUA_POR_KG * valorPeso.longValue();
        Integer x = Math.toIntExact(Math.round(novaQuantidadeDeAgua));

        this.setAguaRecomendacao(Math.round(novaQuantidadeDeAgua));

    }
    public void comecarSeguir(Usuario seguido) {
        this.getSeguindo().add(seguido);
        seguido.getSeguidores().add(this);
    }
    public void deixarDeSeguir(Usuario seguido) {
        this.getSeguindo().remove(seguido);
        seguido.getSeguidores().remove(this);
    }

    public void calcularIMC() {
        if (Objects.nonNull(this.altura)){
            double NORMALIZACAO_IMC_CALCULO_EM_CM = 10000;
            double peso = parseDouble(this.peso);

            Double imc = (peso) / (Math.pow(this.altura,2)) * NORMALIZACAO_IMC_CALCULO_EM_CM;

            this.imc = String.format("%.2f", imc);
        }

    }


}
