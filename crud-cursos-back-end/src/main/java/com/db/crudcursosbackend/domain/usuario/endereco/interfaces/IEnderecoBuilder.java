package com.db.crudcursosbackend.domain.usuario.endereco.interfaces;

import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.estado.Estado;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;

public interface IEnderecoBuilder {
    IEnderecoBuilder numero(String numero);
    IEnderecoBuilder complemento(String complemento);
    IEnderecoBuilder rua(String rua);
    IEnderecoBuilder bairro(String bairro);
    IEnderecoBuilder cidade(String cidade);
    IEnderecoBuilder estado(Estado estado);
    IEnderecoBuilder cep(String cep);
    IEnderecoBuilder pessoa(Pessoa pessoa);
    Endereco build();
    IEnderecoBuilder reset();
}

