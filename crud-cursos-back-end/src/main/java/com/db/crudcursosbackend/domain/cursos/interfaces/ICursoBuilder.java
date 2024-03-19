package com.db.crudcursosbackend.domain.cursos.interfaces;

import java.util.List;
import com.db.crudcursosbackend.domain.base.interfaces.IEntidadeBaseAudicaoBuilder;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;

public interface ICursoBuilder extends IEntidadeBaseAudicaoBuilder{
    ICursoBuilder nome(String nome);
    ICursoBuilder descricao(String descricao);
    ICursoBuilder cargaHoraria(int cargaHoraria);
    ICursoBuilder professor(Professor professor);
    ICursoBuilder alunos(List<Aluno> alunos);
    Curso build();
    ICursoBuilder reset();
}
