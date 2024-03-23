package com.db.crudcursosbackend.domain.usuario.aluno.servicos;

import org.springframework.stereotype.Service;

import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IBuscarAlunoPorCpf;
import com.db.crudcursosbackend.domain.usuario.aluno.repositorios.AlunoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BuscarAlunoPorCpf implements IBuscarAlunoPorCpf {

    private AlunoRepository alunoRepository;

    @Override
    public Aluno buscarPorCpf(String cpf) {
        return alunoRepository.findByCpf(cpf).orElseThrow();
    }
    
}
