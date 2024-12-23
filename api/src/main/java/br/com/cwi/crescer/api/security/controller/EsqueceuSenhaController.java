package br.com.cwi.crescer.api.security.controller;

import br.com.cwi.crescer.api.security.controller.request.AlterarSenhaRequest;
import br.com.cwi.crescer.api.security.controller.request.EsqueceuSenhaRequest;
import br.com.cwi.crescer.api.security.service.AlterarSenhaService;
import br.com.cwi.crescer.api.security.service.EsqueceuSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/esqueceu-senha")
public class EsqueceuSenhaController {
    @Autowired
    private EsqueceuSenhaService esqueceuSenhaService;

    @Autowired
    private AlterarSenhaService alterarSenhaService;

    @PostMapping("/requisitar")
    public void esqueceuSenha(@Valid @RequestBody EsqueceuSenhaRequest request) throws MessagingException, UnsupportedEncodingException {
        esqueceuSenhaService.gerarToken(request);
    }

    @PostMapping("/alterar")
    public void alterarSenha(@Valid @RequestBody AlterarSenhaRequest request){
        alterarSenhaService.alterar(request);
    }

}
