package com.db.crudcursosbackend.domain.usuario.aluno.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IAtivacaoAlunoService;
import com.db.crudcursosbackend.domain.usuario.aluno.repositorios.AlunoRepository;
import com.db.crudcursosbackend.infra.validacoes.ValidacaoEditorUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AtivacaoAlunoService implements IAtivacaoAlunoService {

    private AlunoRepository alunoRepository;

    @Override
    public Aluno desativar(String cpf, Pessoa editor) {
        Aluno aluno = alunoRepository.findByCpf(cpf).orElseThrow();
        ValidacaoEditorUtil.validarDesativacao(editor, aluno);
        return alunoRepository.save(aluno);
    }

    @Override
    public Aluno ativar(String cpf, Pessoa editor) {
        Aluno aluno = alunoRepository.findByCpf(cpf).orElseThrow();
        ValidacaoEditorUtil.validarAtivacao(editor, aluno);
        return alunoRepository.save(aluno);
    }
    

}
