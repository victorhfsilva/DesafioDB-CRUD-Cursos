package com.db.crudcursosbackend.domain.cursos.interfaces;

import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.cursos.Curso;

public interface IAtualizarCursoService {
    Curso atualizar(Long id, Curso novocurso, Pessoa editor);
}
