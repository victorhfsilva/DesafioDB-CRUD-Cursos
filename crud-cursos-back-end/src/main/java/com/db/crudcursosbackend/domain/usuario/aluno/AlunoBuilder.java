package com.db.crudcursosbackend.domain.usuario.aluno;

import java.time.LocalDate;
import java.util.List;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IAlunoBuilder;

import com.db.crudcursosbackend.domain.usuario.pessoa.PessoaBuilder;

public class AlunoBuilder extends PessoaBuilder<AlunoBuilder> implements IAlunoBuilder {

    private String matricula;
    private LocalDate dataDeIngresso;
    private List<Curso> cursos;


    @Override
    public IAlunoBuilder reset() {
        return new AlunoBuilder();
    }

    @Override
    public IAlunoBuilder matricula(String matricula) {
        this.matricula = matricula;
        return self();
    }

    @Override
    public IAlunoBuilder dataDeIngresso(LocalDate dataDeIngresso) {
       this.dataDeIngresso = dataDeIngresso;
       return self();
    }

    @Override
    public IAlunoBuilder cursos(List<Curso> cursos) {
        this.cursos = cursos;
        return self();
    }
   
    @Override
    protected AlunoBuilder self() {
        return this;
    }

    @Override
    public Aluno build() {
        return new Aluno(
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
            matricula,
            dataDeIngresso,
            cursos);
    }
}
