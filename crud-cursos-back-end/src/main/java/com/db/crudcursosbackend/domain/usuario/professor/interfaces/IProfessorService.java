package com.db.crudcursosbackend.domain.usuario.professor.interfaces;

import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;

public interface IProfessorService {
    Professor registrar(Professor pessoa, Pessoa editor);
    Professor atualizar(String cpf, Professor novaPessoa, Pessoa editor);
    Professor excluir(String cpf);
    Professor ativar(String cpf, Pessoa editor);
    Professor desativar(String cpf, Pessoa editor);
    Professor buscarPorCpf(String cpf);
}
