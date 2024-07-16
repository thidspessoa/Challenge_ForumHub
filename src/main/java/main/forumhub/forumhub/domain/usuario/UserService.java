package main.forumhub.forumhub.domain.usuario;

import main.forumhub.forumhub.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//Para Registrar novo Usuario
@Service
public class UserService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario cadastrarNovoUsuario(DadosAutenticacao dados) {
        if (repository.existsByEmail(dados.email())) {
            throw new ValidacaoException("Usuario ja existente");
        }

        Usuario user = new Usuario();

        user.setEmail(dados.email());
        user.setSenha(passwordEncoder.encode(dados.senha()));

        return repository.save(user);
    }
}
