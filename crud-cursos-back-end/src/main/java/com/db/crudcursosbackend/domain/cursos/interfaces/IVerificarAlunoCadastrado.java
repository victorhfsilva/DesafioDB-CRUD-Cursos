package com.db.crudcursosbackend.domain.cursos.interfaces;

public interface IVerificarAlunoCadastrado {
    boolean verficar(String cpfAluno, Long cursoId);
}
