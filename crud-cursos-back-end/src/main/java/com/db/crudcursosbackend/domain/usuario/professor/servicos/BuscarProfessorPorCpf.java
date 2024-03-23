package com.db.crudcursosbackend.domain.usuario.professor.servicos;

import org.springframework.stereotype.Service;

import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IBuscarProfessorPorCpf;
import com.db.crudcursosbackend.domain.usuario.professor.repositorios.ProfessorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BuscarProfessorPorCpf implements IBuscarProfessorPorCpf {

    private ProfessorRepository professorRepository;

    @Override
    public Professor buscarPorCpf(String cpf) {
        return professorRepository.findByCpf(cpf).orElseThrow();
    }
    
}
