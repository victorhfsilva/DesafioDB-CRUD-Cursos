package com.db.crudcursosbackend.domain.usuario.professor.interfaces;

import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;

public interface IRegistrarProfessorService {
    Professor registrar(Professor professor, Pessoa editor);
}
