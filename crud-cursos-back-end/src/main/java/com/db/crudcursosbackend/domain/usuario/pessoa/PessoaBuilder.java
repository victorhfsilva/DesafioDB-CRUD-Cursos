package com.db.crudcursosbackend.domain.usuario.pessoa;

import java.time.LocalDate;
import java.util.List;
import com.db.crudcursosbackend.domain.base.EntidadeBaseAudicaoBuilder;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IPessoaBuilder;

import lombok.Getter;

@Getter
public abstract class PessoaBuilder<T extends PessoaBuilder<T>> extends EntidadeBaseAudicaoBuilder<T> implements IPessoaBuilder{

    private String nome;
    private String sobrenome;
    private String cpf;
    private String senha;
    private Papel papel;
    private LocalDate dataDeNascimento;
    private Contato contato;
    private List<Endereco> enderecos;

    @Override
    public T nome(String nome) {
        this.nome = nome;
        return self();
    }

    @Override
    public T sobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
        return self();
    }

    @Override
    public T cpf(String cpf) {
        this.cpf = cpf;
        return self();
    }

    @Override
    public T senha(String senha) {
        this.senha = senha;
        return self();
    }

    @Override
    public T papel(Papel papel) {
        this.papel = papel;
        return self();
    }

    @Override
    public T dataDeNascimento(LocalDate dataNascimento) {
        this.dataDeNascimento = dataNascimento;
        return self();
    }

    @Override
    public T contato(Contato contato) {
        this.contato = contato;
        return self();
    }

    @Override
    public T enderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
        return self();
    }

}
