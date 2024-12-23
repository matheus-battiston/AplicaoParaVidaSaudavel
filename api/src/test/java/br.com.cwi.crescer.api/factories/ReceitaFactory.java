package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.Receita;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReceitaFactory {

    public static Receita get() {
        return Receita.builder()
                .id(SimpleFactory.getRandomLong())
                .dataCriacao(LocalDate.now())
                .usuario(UsuarioFactory.get())
                .nota("0")
                .privado(false)
                .alimentos(new ArrayList<>())
                .avaliacoes(new ArrayList<>())
                .build();
    }




}
