INSERT INTO contatos (id, email, celular) VALUES
(1, 'pessoa1@example.com', '123456789'),
(2, 'pessoa2@example.com', '987654321'),
(3, 'pessoa3@example.com', '111111111');

INSERT INTO pessoas (id, ativo, nome, sobrenome, cpf, senha, papel, data_nascimento, contato_id) VALUES
(1, true, 'Professor', 'Um', '111.111.111-11', 'senha789', 'ADMIN', '1980-01-01', 1);

INSERT INTO professores (salario, id) VALUES
(5000.00, 1);

INSERT INTO cursos (id, ativo, nome, descricao, carga_horaria, professor_id) VALUES
(1, true, 'Curso de Matemática', 'Curso introdutório de Matemática', 40, 1),
(2, true, 'Curso de História', 'Curso introdutório de História', 30, 1);

INSERT INTO pessoas (id, ativo, nome, sobrenome, cpf, senha, papel, data_nascimento, contato_id) VALUES
(2, true, 'Aluno', 'Um', '222.222.222-22', 'senha789', 'USUARIO', '2000-01-01', 2),
(3, true, 'Aluno', 'Dois', '333.333.333-33', 'senha789', 'USUARIO', '2000-01-02', 3);

INSERT INTO alunos (matricula, data_ingresso, id) VALUES
('A001', '2020-01-01', 2),
('A002', '2020-01-02', 3);

INSERT INTO enderecos (id, numero, complemento, rua, bairro, cidade, estado, cep, pessoa_id) VALUES
(1, '100', 'Apto 101', 'Rua A', 'Bairro 1', 'Cidade 1', 'ACRE', '12345-678', 1),
(2, '200', 'Casa', 'Rua B', 'Bairro 2', 'Cidade 2', 'ALAGOAS', '98765-432', 2),
(3, '300', 'Loja 1', 'Rua C', 'Bairro 3', 'Cidade 3', 'AMAPA', '13579-246', 3);

INSERT INTO alunos_cursos (aluno_id, curso_id) VALUES
(2, 1), 
(3, 1),
(3, 2);