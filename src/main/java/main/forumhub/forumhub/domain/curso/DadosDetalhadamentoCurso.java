package main.forumhub.forumhub.domain.curso;

public record DadosDetalhadamentoCurso(
        Long id,
        String nome,
        Categoria categoria

) {
    public DadosDetalhadamentoCurso(Curso curso) {
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
