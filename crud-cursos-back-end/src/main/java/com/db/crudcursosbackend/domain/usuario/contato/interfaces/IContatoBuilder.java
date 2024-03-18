package com.db.crudcursosbackend.domain.usuario.contato.interfaces;

import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;

public interface IContatoBuilder {
    IContatoBuilder email(String email);
    IContatoBuilder celular(String celular);
    IContatoBuilder pessoa(Pessoa pessoa);
    Contato build(); 
    IContatoBuilder reset();
}
