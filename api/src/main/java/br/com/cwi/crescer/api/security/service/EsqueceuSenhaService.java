package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.config.BuscarUsuarioSecuritySerivce;
import br.com.cwi.crescer.api.security.controller.request.EsqueceuSenhaRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EsqueceuSenhaService {
    public static final String link = "http::3000/resetar-senha";
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private BuscarUsuarioSecuritySerivce buscarUsuarioSecuritySerivce;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void gerarToken(EsqueceuSenhaRequest request) throws MessagingException, UnsupportedEncodingException {
        Usuario usuario = buscarUsuarioSecuritySerivce.porEmail(request.getEmail());
        String token = RandomString.make(30);
        usuario.setTokenSenha(token);
        sendEmail(request.getEmail(), link, token);
        usuarioRepository.save(usuario);
    }


    public void sendEmail(String recipientEmail, String link, String codigo) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("alexandria-crescer@outlook.com", "Support");
        helper.setTo(recipientEmail);

        String subject = "Resetar senha";

        String content = "<p>Ola,</p>"
                + "<p>Voce fez um pedido para alterar sua senha.</p>"
                + "<p>Clique no link abaixo para resetar:</p>"
                + "<p>Seu codigo é: " + codigo
                + "<p><a href=\"" + link + "\">Alterar senha</a></p>"
                + "<br>"
                + "<p>Ignore esse email se voce nao fez o pedido</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
