package main.forumhub.forumhub.domain.topico;

public record DadosAtualizacaoTopico(
        String mensagem,
        String titulo) {
    public DadosAtualizacaoTopico(Topico topico){
        this(topico.getMensagem(), topico.getTitulo());
    }
}
