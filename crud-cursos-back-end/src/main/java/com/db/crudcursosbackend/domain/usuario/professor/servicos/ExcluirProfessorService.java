package com.db.crudcursosbackend.domain.usuario.professor.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IExcluirProfessorService;
import com.db.crudcursosbackend.domain.usuario.professor.repositorios.ProfessorRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ExcluirProfessorService implements IExcluirProfessorService {

    private ProfessorRepository professorRepository;
    private CursoRepository cursoRepository;

    @Override
    public Professor excluir(String cpf) {

        Professor professor = professorRepository.findByCpf("11111111111").orElseThrow();
        List<Curso> cursos = professor.getCursos();

        if (cursos != null){
            cursos.forEach(curso -> {
                curso.setAlunos(List.of());
                cursoRepository.save(curso);
                cursoRepository.delete(curso);
            });
        }

        Professor pessoa = professorRepository.findByCpf(cpf).orElseThrow();
        professorRepository.delete(pessoa);
        return pessoa;
    }
    
}
