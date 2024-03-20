package com.db.crudcursosbackend.domain.cursos.interfaces;

import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;

public interface IAtualizarProfessorCursoService {
    Curso atualizar(Long id, String cpf, Pessoa editor);
}
