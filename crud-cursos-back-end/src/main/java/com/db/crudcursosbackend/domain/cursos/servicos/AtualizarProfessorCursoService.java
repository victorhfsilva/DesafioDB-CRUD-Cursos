package com.db.crudcursosbackend.domain.cursos.servicos;

import org.springframework.stereotype.Service;

import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.interfaces.IAtualizarProfessorCursoService;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.repositorios.ProfessorRepository;
import com.db.crudcursosbackend.infra.excecoes.EntidadeDesativada;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AtualizarProfessorCursoService implements IAtualizarProfessorCursoService{

    private CursoRepository cursoRepository;
    private ProfessorRepository professorRepository;
    
    @Override
    public Curso atualizar(Long id, String cpf, Pessoa editor) {
        
        Curso cursoSalvo = cursoRepository.findById(id).orElseThrow();
        
        Professor novoProfessor = professorRepository.findByCpf(cpf).orElseThrow();
        
        if (novoProfessor.isAtivo()){
            cursoSalvo.setProfessor(novoProfessor);  
            return cursoRepository.save(cursoSalvo);
        }
        throw new EntidadeDesativada("O professor com cpf " + cpf + " foi desativada.");        
    }

    
}
