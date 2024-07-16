package main.forumhub.forumhub.controllers;

import jakarta.validation.Valid;
import main.forumhub.forumhub.domain.ValidacaoException;
import main.forumhub.forumhub.domain.curso.CursoRepository;
import main.forumhub.forumhub.domain.topico.*;
import main.forumhub.forumhub.domain.usuario.Usuario;
import main.forumhub.forumhub.domain.usuario.UsuarioRepository;
import main.forumhub.forumhub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    TopicoService topicoService;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrarTopico(@RequestHeader("Authorization") String token, @RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {
        var email = tokenService.getSubject(token.replace("Bearer ", "").trim());
        Usuario autor = usuarioRepository.getReferenceByEmail(email);
        var curso = cursoRepository.localizarCurso(dados.nomeCurso());
        if (curso == null) {
            throw new ValidacaoException(" O Curso nao consta na nossa lista de cursos cadastrado ");
        }

        var topico = new Topico(null, dados.titulo(), dados.mensagem(), LocalDateTime.now(), EstadoDoTopico.AGUARDANDO, autor, curso, null);
        topicoRepository.save(topico);
        var uri = uriBuilder.path("topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhadamentoTopico(topico));

    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarTopico(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody @Valid DadosCadastroTopico dados) {
        var topico = topicoService.autenticarAutor(token, id);
        topico.atualizarInformacoes(dados);

        return ResponseEntity.ok().body(new DadosAtualizacaoTopico(topico));
    }


    @GetMapping
    public ResponseEntity<Page<DadosListarTopicos>> listarTopicos(@PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {
        var page = topicoRepository.findAll(paginacao).map(DadosListarTopicos::new);
        return ResponseEntity.ok(page);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity apagarTopico(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        var topico = topicoService.autenticarAutor(token, id);
        topicoRepository.delete(topico);
        return ResponseEntity.ok().build();
    }
}

