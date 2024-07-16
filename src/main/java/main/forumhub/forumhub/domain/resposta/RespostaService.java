package main.forumhub.forumhub.domain.resposta;


import main.forumhub.forumhub.domain.ValidacaoException;
import main.forumhub.forumhub.domain.topico.Topico;
import main.forumhub.forumhub.domain.topico.TopicoRepository;
import main.forumhub.forumhub.domain.usuario.Usuario;
import main.forumhub.forumhub.domain.usuario.UsuarioRepository;
import main.forumhub.forumhub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespostaService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private  RespostaRespository respostaRespository;

    public Usuario encontrar_nomearAutor(String token, String nome) {
        var email = tokenService.getSubject(token.replace("Bearer ", "").trim());
        Usuario usuario = usuarioRepository.getReferenceByEmail(email);
        usuario.setNome(nome);
        usuarioRepository.save(usuario);

        return usuario;
    }


    public Topico localizarTopico(Long idTopico) {
        var topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new ValidacaoException("Topico nao localizado pelo ID informado"));

        return topico;
    }

    public Resposta autenticarAutor(String token, Long id) {
        var email = tokenService.getSubject(token.replace("Bearer ", ""));
        var autor = (Usuario) usuarioRepository.findByEmail(email);
        var resposta = respostaRespository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Resposta nao localizada pelo ID informado"));

        if(!autor.getId().equals(resposta.getAutor().getId())){
            throw new ValidacaoException("Voce nao possui autorizacao para alterar, modificar ou deletar essa Resposta");
        }

        return resposta;

    }

    public void deletarResposta(String token, Long id) {
        var email = tokenService.getSubject(token.replace("Bearer ", ""));
        var autor = (Usuario) usuarioRepository.findByEmail(email);
        var resposta = respostaRespository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Resposta nao localizada pelo ID informado"));

        if(!autor.getId().equals(resposta.getAutor().getId())){
            throw new ValidacaoException("Voce nao possui autorizacao para alterar, modificar ou deletar essa Resposta");
        }

        resposta.setStatus(StatusResposta.FECHADO);

    }
}
