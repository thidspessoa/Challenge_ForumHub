package main.forumhub.forumhub.domain.topico;

import main.forumhub.forumhub.domain.resposta.DadosListarRespostas;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DadosListarTopicos(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime data,
        EstadoDoTopico estadoDoTopico,
       List<DadosListarRespostas> respostas
) {
    public DadosListarTopicos(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getEstadoDoTopico(),topico.getResposta().stream().map(DadosListarRespostas::new).collect(Collectors.toUnmodifiableList()));
    }
}
