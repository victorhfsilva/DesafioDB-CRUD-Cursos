package com.db.crudcursosbackend.domain.cursos.interfaces;

public interface IVerificarAlunoCadastradoService {
    boolean verficar(String cpfAluno, Long cursoId);
}
