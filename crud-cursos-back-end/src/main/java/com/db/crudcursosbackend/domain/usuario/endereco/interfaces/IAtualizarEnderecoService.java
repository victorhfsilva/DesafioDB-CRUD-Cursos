package com.db.crudcursosbackend.domain.usuario.endereco.interfaces;

import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;

public interface IAtualizarEnderecoService {
    Endereco atualizar(Long id, Endereco novoEndereco);
}
