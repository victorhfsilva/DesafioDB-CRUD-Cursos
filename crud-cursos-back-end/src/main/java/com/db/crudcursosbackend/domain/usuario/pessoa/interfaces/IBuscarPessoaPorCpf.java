package com.db.crudcursosbackend.domain.usuario.pessoa.interfaces;

import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;

public interface IBuscarPessoaPorCpf {
    Pessoa buscarPorCpf(String cpf);
}
