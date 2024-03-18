package com.db.crudcursosbackend.domain.usuario.aluno.interfaces;

import java.time.LocalDate;
import java.util.List;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IPessoaBuilder;

public interface IAlunoBuilder extends IPessoaBuilder {
    IAlunoBuilder matricula(String matricula);
    IAlunoBuilder dataDeIngresso(LocalDate dataDeIngresso);
    IAlunoBuilder cursos(List<Curso> cursos);
    Aluno build();
    IAlunoBuilder reset();
}
