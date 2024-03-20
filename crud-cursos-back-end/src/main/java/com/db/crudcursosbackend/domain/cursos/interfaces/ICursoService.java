package com.db.crudcursosbackend.domain.cursos.interfaces;

import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.cursos.Curso;

public interface ICursoService {
    Curso registrar(Curso curso, Pessoa editor);
    Curso atualizar(Long cursoId, Curso novoALuno, Pessoa editor);
    Curso excluir(Long cursoId);
    Curso adicionarAluno(Long cursoId, String cpf);
    Curso removerAluno(Long cursoId, String cpf);
}
