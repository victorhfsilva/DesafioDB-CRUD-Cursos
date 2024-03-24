INSERT INTO contatos (id, email, celular) VALUES
(1, 'pessoa1@example.com', '123456789'),
(2, 'pessoa2@example.com', '987654321'),
(3, 'pessoa3@example.com', '111111111'),
(4, 'pessoa4@example.com', '222222222');

INSERT INTO pessoas (id, ativo, nome, sobrenome, cpf, senha, papel, data_nascimento, contato_id) VALUES
(1, true, 'Professor', 'Um', '11111111111', '$2a$10$IxEFI7X5WIQAfvAirDMsn.CEB5cBO3vnBqxeRyEMmtx8fMvYDAbR2', 'ADMIN', '1980-01-01', 1),
(4, true, 'Professor', 'Dois', '44444444444', '$2a$10$QHchy/FKqlw2AYbS4MLFfu8wAYrSW6rtG.QgYuBRkNdet7OBHKpCy', 'ADMIN', '1985-01-01', 4);

INSERT INTO professores (salario, id) VALUES
(5000.00, 1),
(6000.00, 4);

INSERT INTO cursos (id, ativo, nome, descricao, carga_horaria, professor_id) VALUES
(1, true, 'Curso de Matemática', 'Curso introdutório de Matemática', 40, 1),
(2, true, 'Curso de História', 'Curso introdutório de História', 30, 1),
(3, true, 'Curso de Biologia', 'Curso introdutório de Biologia', 45, 4),
(4, true, 'Curso de Geografia', 'Curso introdutório de Geografia', 35, 4);

INSERT INTO pessoas (id, ativo, nome, sobrenome, cpf, senha, papel, data_nascimento, contato_id) VALUES
(2, true, 'Aluno', 'Um', '22222222222', '$2a$10$706THYvOENiTmnCajGKiIOKKmF8reqf6bJGmQEiIF37R8aA/J6tyO', 'USUARIO', '2000-01-01', 2),
(3, true, 'Aluno', 'Dois', '33333333333', '$2a$10$PVm0s0ap2cL4iLSj5evHXOurDresp9TzL7QmPqqEK4.XzBZKuoRsu', 'ADMIN', '2000-01-02', 3);

INSERT INTO alunos (matricula, data_ingresso, id) VALUES
('A001', '2020-01-01', 2),
('A002', '2020-01-02', 3);

INSERT INTO enderecos (numero, complemento, rua, bairro, cidade, estado, cep, pessoa_id) VALUES
('100', 'Apto 101', 'Rua A', 'Bairro 1', 'Cidade 1', 'ACRE', '12345678', 1),
('200', 'Casa', 'Rua B', 'Bairro 2', 'Cidade 2', 'ALAGOAS', '98765432', 2),
('300', 'Loja 1', 'Rua C', 'Bairro 3', 'Cidade 3', 'AMAPA', '13579246', 3),
('400', 'Casa', 'Rua D', 'Bairro 4', 'Cidade 4', 'BAHIA', '13545246', 4),
('500', 'Casa', 'Rua H', 'Bairro 5', 'Cidade 5', 'BAHIA', '13565246', 4);

INSERT INTO cursos_alunos (aluno_id, curso_id) VALUES
(2, 1), 
(3, 1),
(3, 2);