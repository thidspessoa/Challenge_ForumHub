package main.forumhub.forumhub.domain.resposta;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RespostaRespository extends JpaRepository<Resposta, Long> {

}
