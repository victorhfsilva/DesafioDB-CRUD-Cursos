package com.db.crudcursosbackend.domain.cursos.interfaces;

import com.db.crudcursosbackend.domain.cursos.Curso;

public interface IRemoverAlunoService {
    Curso remover(Long cursoId, String cpf);
}
