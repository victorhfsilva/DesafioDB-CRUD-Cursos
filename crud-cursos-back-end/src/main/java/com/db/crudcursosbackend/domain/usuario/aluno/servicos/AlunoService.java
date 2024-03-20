package com.db.crudcursosbackend.domain.usuario.aluno.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IAlunoService;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IAtualizarAlunoService;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IExcluirAlunoService;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IRegistrarAlunoService;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AlunoService implements IAlunoService {

    private IAtualizarAlunoService atualizarAlunoService;
    private IExcluirAlunoService excluirAlunoService;
    private IRegistrarAlunoService registrarAlunoService;

    @Override
    public Aluno registrar(Aluno aluno, Pessoa editor) {
        return registrarAlunoService.registrar(aluno, editor);
    }

    @Override
    public Aluno atualizar(String cpf, Aluno novoAluno, Pessoa editor) {
        return atualizarAlunoService.atualizar(cpf, novoAluno, editor);
    }

    @Override
    public Aluno excluir(String cpf) {
        return excluirAlunoService.excluir(cpf);
    }
    
}