package com.db.crudcursosbackend.domain.usuario.endereco.interfaces;

import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;

public interface IBuscarEnderecoPorId {
    Endereco buscarPorId(Long id);
}
