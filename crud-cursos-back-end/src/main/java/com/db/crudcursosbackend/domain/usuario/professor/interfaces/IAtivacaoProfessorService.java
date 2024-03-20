package com.db.crudcursosbackend.domain.usuario.professor.interfaces;

import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;

public interface IAtivacaoProfessorService {
    Professor desativar(String cpf, Pessoa editor);
    Professor ativar(String cpf, Pessoa editor);
}
