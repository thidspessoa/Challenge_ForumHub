package main.forumhub.forumhub.domain.curso;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DadosCadastroCursos(
        @JsonAlias("nomeCurso")
        @NotBlank
        String nome,
        @NotNull
        Categoria categoria

) {
}
