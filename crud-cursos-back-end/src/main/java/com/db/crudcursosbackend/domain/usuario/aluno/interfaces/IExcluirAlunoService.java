package com.db.crudcursosbackend.domain.usuario.aluno.interfaces;

import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;

public interface IExcluirAlunoService {
    Aluno excluir(String cpf);
}
