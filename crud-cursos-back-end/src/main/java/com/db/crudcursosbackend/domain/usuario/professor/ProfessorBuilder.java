package com.db.crudcursosbackend.domain.usuario.professor;

import java.util.List;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.usuario.pessoa.PessoaBuilder;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IProfessorBuilder;

public class ProfessorBuilder extends PessoaBuilder<ProfessorBuilder> implements IProfessorBuilder {

    private double salario;
    
    private List<Curso> cursos;

    @Override
    public IProfessorBuilder salario(double salario) {
        this.salario = salario;
        return self();
    }

    @Override
    public IProfessorBuilder cursos(List<Curso> cursos) {
        this.cursos = cursos;
        return self();
    }

    @Override
    public Professor build() {

        return new Professor(
            isAtivo(),
            getCriadoPor(), 
            getAtualizadoPor(), 
            getDesativadoPor(), 
            getCriadoAs(), 
            getAtualizadoAs(), 
            getDesativadoAs(),
            getNome(), 
            getSobrenome(), 
            getCpf(), 
            getSenha(), 
            getPapel(), 
            getDataDeNascimento(),
            getContato(),
            getEnderecos(),
            salario,
            cursos);
    }

    @Override
    public ProfessorBuilder reset() {
        return new ProfessorBuilder();
    }

    @Override
    protected ProfessorBuilder self() {
        return this;
    }
    
}
