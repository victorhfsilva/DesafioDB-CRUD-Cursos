package com.db.crudcursosbackend.domain.usuario.pessoa.interfaces;

import com.db.crudcursosbackend.domain.base.interfaces.IEntidadeBaseAudicaoBuilder;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import java.time.LocalDate;
import java.util.List;

public interface IPessoaBuilder extends IEntidadeBaseAudicaoBuilder {
    IPessoaBuilder nome(String nome);
    IPessoaBuilder sobrenome(String sobrenome);
    IPessoaBuilder cpf(String cpf);
    IPessoaBuilder senha(String senha);
    IPessoaBuilder papel(Papel papel);
    IPessoaBuilder dataDeNascimento(LocalDate dataNascimento);
    IPessoaBuilder contato(Contato contato);
    IPessoaBuilder enderecos(List<Endereco> enderecos);
}
