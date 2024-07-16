package main.forumhub.forumhub.domain.topico;

import main.forumhub.forumhub.domain.ValidacaoException;
import main.forumhub.forumhub.domain.usuario.UsuarioRepository;
import main.forumhub.forumhub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private  TopicoRepository topicoRepository;


    public Topico autenticarAutor(String token, Long id) {
        var email = tokenService.getSubject(token.replace("Bearer ", ""));
        var autor = usuarioRepository.findByEmail(email);
        var topico = topicoRepository.getReferenceById(id);

        if(topico == null){
            throw new ValidacaoException("Topico nao encontrado pelo o Id informado");
        }

        if(autor != topico.getAutor()){
            throw new ValidacaoException("Voce nao possui autorizacao para alterar, modificar ou deletar esse topico");
        }

        return topico;

    }

}
