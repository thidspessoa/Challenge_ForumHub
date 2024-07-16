package main.forumhub.forumhub.domain.topico;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosDetalhadamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao
) {
    public DadosDetalhadamentoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao());
    }
}
