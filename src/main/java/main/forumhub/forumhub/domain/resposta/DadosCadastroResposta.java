package main.forumhub.forumhub.domain.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroResposta(
        @NotBlank
        String nome,
        @NotNull
        Long idTopico,
        @NotBlank
        String mensagem
) {
}

