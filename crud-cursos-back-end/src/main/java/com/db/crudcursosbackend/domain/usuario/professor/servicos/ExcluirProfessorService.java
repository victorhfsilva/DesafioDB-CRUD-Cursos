package com.db.crudcursosbackend.domain.usuario.professor.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IExcluirProfessorService;
import com.db.crudcursosbackend.domain.usuario.professor.repositorios.ProfessorRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ExcluirProfessorService implements IExcluirProfessorService {

    private ProfessorRepository professorRepository;

    @Override
    public Professor excluir(String cpf) {

        Professor professor = professorRepository.findByCpf(cpf).orElseThrow();

        professorRepository.delete(professor);
        return professor;
    }
    
}
