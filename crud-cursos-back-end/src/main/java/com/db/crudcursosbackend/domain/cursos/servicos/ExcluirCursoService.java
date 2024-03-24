package com.db.crudcursosbackend.domain.cursos.servicos;

import org.springframework.stereotype.Service;

import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.interfaces.IExcluirCursoService;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExcluirCursoService implements IExcluirCursoService {

    private CursoRepository cursoRepository;

    @Override
    public Curso excluir(Long id) {
        Curso curso = cursoRepository.findById(id).orElseThrow();
        cursoRepository.delete(curso);
        return curso;
    }
    
}
