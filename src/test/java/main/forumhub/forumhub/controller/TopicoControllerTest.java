package main.forumhub.forumhub.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import main.forumhub.forumhub.domain.curso.CursoRepository;
import main.forumhub.forumhub.domain.resposta.RespostaRespository;
import main.forumhub.forumhub.domain.topico.*;
import main.forumhub.forumhub.domain.usuario.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TopicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroTopico> dadosCadastroTopicoJson;

    @Autowired
    private JacksonTester<DadosDetalhadamentoTopico> dadosDetalhadamentoTopicoJson;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private RespostaRespository respostaRespository;

    @MockBean
    private TopicoRepository topicoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    @DisplayName("Deveria devolver codito http 400 quando informacoes estao invalidas")
    @WithMockUser
    void cadastrar_cenario1() throws Exception {
        var response = mvc
                .perform(post("/topicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Deveria devolver codito http 200 quando informacoes estao corretas")
    @WithMockUser
    void cadastrar_cenario2() throws Exception{
        var autor = usuarioRepository.getReferenceByEmail("maria@email.com");
        var curso = cursoRepository.localizarCurso("Java");

        var dadosCadastro = new DadosCadastroTopico(
                "Teste",
                "Teste",
                "Java"
        );

        var topico = new Topico(null, dadosCadastro.titulo(), dadosCadastro.mensagem(), LocalDateTime.now(), EstadoDoTopico.AGUARDANDO, autor, curso, null);

        when(topicoRepository.save(any())).thenReturn(topico);


        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJmb3J1bWh1YiIsInN1YiI6Im1hcmlhQGVtYWlsLmNvbSIsImV4cCI6MTcxODU4MjkzMX0.jFZ043_ZMc3MzE83LItmpqq1z9bFhpDhNT-6UqzNTYU"; // Replace with a valid mock token

        var response = mvc
                .perform(post("/topicos")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroTopicoJson.write(dadosCadastro).getJson()))
                .andReturn().getResponse();

        var dadosDetalhadamento = new DadosDetalhadamentoTopico(topico);

        DadosDetalhadamentoTopico actualResponse = objectMapper.readValue(response.getContentAsString(), DadosDetalhadamentoTopico.class);


//        var jsonEsperado = dadosDetalhadamentoTopicoJson.write(dadosDetalhadamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(actualResponse.id()).isEqualTo(dadosDetalhadamento.id());
        assertThat(actualResponse.titulo()).isEqualTo(dadosDetalhadamento.titulo());




    }
}
