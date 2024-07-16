package main.forumhub.forumhub.domain.usuario;

public record DadosRegitro(String email) {
    public DadosRegitro(Usuario newUser){
        this(newUser.getEmail());
    }
}
