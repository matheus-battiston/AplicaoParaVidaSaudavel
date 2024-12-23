package br.com.cwi.crescer.api.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Getter @Setter
public class UsuarioRequest {

    @NotBlank
    private String nome;

    @NotNull @Email
    private String email;

    @NotBlank
    private String senha;


    @NotNull
    private LocalDate dataNascimento;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;


}
