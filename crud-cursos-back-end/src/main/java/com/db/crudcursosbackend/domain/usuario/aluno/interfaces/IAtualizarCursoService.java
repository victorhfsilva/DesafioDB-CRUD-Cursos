package com.db.crudcursosbackend.domain.usuario.aluno.interfaces;

import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;

public interface IAtualizarCursoService {
    Aluno atualizar(String cpf, Aluno novoaluno, Pessoa editor);
}
