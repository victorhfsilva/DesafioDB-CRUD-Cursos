package com.db.crudcursosbackend.domain.usuario.professor.interfaces;

import java.util.List;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IPessoaBuilder;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;

public interface IProfessorBuilder extends IPessoaBuilder {
    IProfessorBuilder salario(double salario);
    IProfessorBuilder cursos(List<Curso> cursos);
    Professor build();
    IProfessorBuilder reset();
}
