package com.db.crudcursosbackend.domain.usuario.aluno.interfaces;

import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;

public interface IAlunoService {
    Aluno registrar(Aluno pessoa, Pessoa editor);
    Aluno atualizar(String cpf, Aluno novaPessoa, Pessoa editor);
    Aluno excluir(String cpf);
}
