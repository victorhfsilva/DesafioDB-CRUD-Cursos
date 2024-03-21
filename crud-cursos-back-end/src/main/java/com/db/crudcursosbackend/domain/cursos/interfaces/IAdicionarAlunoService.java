package com.db.crudcursosbackend.domain.cursos.interfaces;

import com.db.crudcursosbackend.domain.cursos.Curso;

public interface IAdicionarAlunoService {
    Curso adicionar(Long cursoId, String cpf);
}
