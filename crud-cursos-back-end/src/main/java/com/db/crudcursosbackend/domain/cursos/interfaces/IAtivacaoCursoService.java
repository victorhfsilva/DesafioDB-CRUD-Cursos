package com.db.crudcursosbackend.domain.cursos.interfaces;

import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;

public interface IAtivacaoCursoService {
    Curso desativar(Long id, Pessoa editor);
    Curso ativar(Long id, Pessoa editor);
}
