package com.db.crudcursosbackend.domain.cursos;

import java.util.List;
import com.db.crudcursosbackend.domain.base.EntidadeBaseAudicaoBuilder;
import com.db.crudcursosbackend.domain.cursos.interfaces.ICursoBuilder;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;

public class CursoBuilder extends EntidadeBaseAudicaoBuilder<CursoBuilder> implements ICursoBuilder {

    private String nome;
    private String descricao; 
    private int cargaHoraria;
    private Professor professor;
    List<Aluno> alunos;

    @Override
    public ICursoBuilder nome(String nome) {
        this.nome = nome;
        return self();
    }

    @Override
    public ICursoBuilder descricao(String descricao) {
        this.descricao = descricao;
        return self();
    }

    @Override
    public ICursoBuilder cargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
        return self();
    }

    @Override
    public ICursoBuilder professor(Professor professor) {
        this.professor = professor;
        return self();
    }

    @Override
    public ICursoBuilder alunos(List<Aluno> alunos) {
       this.alunos = alunos;
       return self();
    }

    @Override
    public Curso build() {
        return new Curso(  
            isAtivo(),
            getCriadoPor(), 
            getAtualizadoPor(), 
            getDesativadoPor(), 
            getCriadoAs(), 
            getAtualizadoAs(), 
            getDesativadoAs(),
            nome,
            descricao,
            cargaHoraria,
            professor,
            alunos);
    }

    @Override
    public ICursoBuilder reset() {
        return new CursoBuilder();
    }

    @Override
    protected CursoBuilder self() {
        return this;
    }
    
}
