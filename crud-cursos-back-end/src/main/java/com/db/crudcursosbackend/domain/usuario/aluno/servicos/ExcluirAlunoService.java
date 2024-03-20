package com.db.crudcursosbackend.domain.usuario.aluno.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IExcluirAlunoService;
import com.db.crudcursosbackend.domain.usuario.aluno.repositorios.AlunoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ExcluirAlunoService implements IExcluirAlunoService {

    private AlunoRepository alunoRepository;

    @Override
    public Aluno excluir(String cpf) {

        Aluno aluno = alunoRepository.findByCpf(cpf).orElseThrow();
        alunoRepository.delete(aluno);
        return aluno;
    }
    
}
