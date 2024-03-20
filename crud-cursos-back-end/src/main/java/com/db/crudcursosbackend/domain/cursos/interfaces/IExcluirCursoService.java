package com.db.crudcursosbackend.domain.cursos.interfaces;

import com.db.crudcursosbackend.domain.cursos.Curso;

public interface IExcluirCursoService {
    Curso excluir(Long id);
}
