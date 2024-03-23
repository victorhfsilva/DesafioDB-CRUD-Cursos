package com.db.crudcursosbackend.domain.usuario.professor.interfaces;

import com.db.crudcursosbackend.domain.usuario.professor.Professor;

public interface IBuscarProfessorPorCpf {
    Professor buscarPorCpf(String cpf);
}
