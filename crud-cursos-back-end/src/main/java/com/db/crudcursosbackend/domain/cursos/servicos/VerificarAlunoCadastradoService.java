package com.db.crudcursosbackend.domain.cursos.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.interfaces.IVerificarAlunoCadastradoService;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VerificarAlunoCadastradoService implements IVerificarAlunoCadastradoService{
    
    private CursoRepository cursoRepository;

    @Override
    public boolean verficar(String cpfAluno, Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId).orElseThrow();
        List<Aluno> alunos = curso.getAlunos(); 
        if (alunos != null){
            return !alunos.stream().filter(aluno -> aluno.getCpf().equals(cpfAluno)).toList().isEmpty();
        }
        return false;
    }

}
