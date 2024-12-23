package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.domain.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findAllByConversa(Conversa conversa);
}
