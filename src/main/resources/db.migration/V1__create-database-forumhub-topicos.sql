
create database forumhub;


CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100),
    email VARCHAR(250),
    senha VARCHAR(250),
    PRIMARY KEY (id)
);

CREATE TABLE cursos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    dataCriacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estadoDoTopico VARCHAR(100) NOT NULL,
    usuarios_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    resposta TEXT,

    PRIMARY KEY (id),
    CONSTRAINT fk_topicos_usuarios_id FOREIGN KEY (usuarios_id) REFERENCES usuarios(id),
    CONSTRAINT fk_topicos_cursos_id FOREIGN KEY (curso_id) REFERENCES cursos(id)
);


CREATE TABLE respostas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensagme TEXT NOT NULL,
    topico_id BIGINT NOT NULL,
    dataCriacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuarios_id BIGINT NOT NULL,
    solucao TEXT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_respostas_topico_id FOREIGN KEY (topico_id) REFERENCES topicos(id),
    CONSTRAINT fk_respostas_usuarios_id FOREIGN KEY (usuarios_id) REFERENCES usuarios(id)
);










