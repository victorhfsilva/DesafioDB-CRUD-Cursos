package com.db.crudcursosbackend.domain.cursos.interfaces;

import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.cursos.Curso;

public interface IRegistrarCursoService {
    Curso registrar(Curso curso, Pessoa editor);
}
