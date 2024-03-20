package com.db.crudcursosbackend.domain.usuario.professor.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IAtualizarProfessorService;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IExcluirProfessorService;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IProfessorService;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IRegistrarProfessorService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProfessorService implements IProfessorService {
    
    private IRegistrarProfessorService registrarProfessorService;
    private IAtualizarProfessorService atualizarProfessorService;
    private IExcluirProfessorService excluirProfessorService;

    public Professor registrar(Professor pessoa, Pessoa editor) {
       return registrarProfessorService.registrar(pessoa, editor);
    }

    @Override
    public Professor atualizar(String cpf, Professor novaPessoa, Pessoa editor) {
        return atualizarProfessorService.atualizar(cpf, novaPessoa, editor);
    }

    @Override
    public Professor excluir(String cpf) {
       return excluirProfessorService.excluir(cpf);
    }

    @Override
    public Professor ativar(String cpf, Pessoa editor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ativar'");
    }

    @Override
    public Professor desativar(String cpf, Pessoa editor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'desativar'");
    }

}
