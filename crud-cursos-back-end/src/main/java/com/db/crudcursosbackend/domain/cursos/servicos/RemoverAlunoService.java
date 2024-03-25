package com.db.crudcursosbackend.domain.cursos.servicos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.interfaces.IRemoverAlunoService;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.repositorios.AlunoRepository;
import com.db.crudcursosbackend.infra.excecoes.EntidadeDesativada;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RemoverAlunoService implements IRemoverAlunoService {
    
    private CursoRepository cursoRepository;
    
    /* 
     * TO-DO: Resolver como remover um aluno no relacionamento Many-to-Many
    */
    @Override
    public Curso remover(Long cursoId, String cpf) {
        Curso curso = cursoRepository.findById(cursoId).orElseThrow();
        List<Aluno> alunos = curso.getAlunos().stream().filter(aluno -> !aluno.getCpf().equals(cpf)).toList();
        curso.setAlunos(alunos);
        return cursoRepository.save(curso);
    }
}
    
