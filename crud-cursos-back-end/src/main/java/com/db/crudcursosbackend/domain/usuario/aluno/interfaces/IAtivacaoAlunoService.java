package com.db.crudcursosbackend.domain.usuario.aluno.interfaces;

import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;

public interface IAtivacaoAlunoService {
    Aluno desativar(String cpf, Pessoa editor);
    Aluno ativar(String cpf, Pessoa editor);
}
