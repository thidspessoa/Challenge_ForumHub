package main.forumhub.forumhub.controllers;

import jakarta.validation.Valid;
import main.forumhub.forumhub.domain.ValidacaoException;
import main.forumhub.forumhub.domain.usuario.DadosAutenticacao;
import main.forumhub.forumhub.domain.usuario.DadosRegitro;
import main.forumhub.forumhub.domain.usuario.UserService;
import main.forumhub.forumhub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cadastrar")
public class RegistroController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity registrarUsuario(@RequestBody @Valid DadosAutenticacao dados) {
        try {
            Usuario newUser = userService.cadastrarNovoUsuario(dados);
            return ResponseEntity.status(HttpStatus.CREATED).body(new DadosRegitro(newUser));
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

