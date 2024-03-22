package com.db.crudcursosbackend.domain.usuario.pessoa.interfaces;

import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;

public interface IAtualizarPessoaService {
    Pessoa atualizar(String cpf, Pessoa novaPessoa, Pessoa editor);
}
