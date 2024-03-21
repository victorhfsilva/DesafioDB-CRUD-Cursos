package com.db.crudcursosbackend.domain.usuario.professor.servicos;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IAtivacaoProfessorService;
import com.db.crudcursosbackend.domain.usuario.professor.repositorios.ProfessorRepository;
import com.db.crudcursosbackend.infra.validacoes.ValidacaoEditorUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AtivacaoProfessorService implements IAtivacaoProfessorService {

    private ProfessorRepository professorRepository;

    @Override
    public Professor desativar(String cpf, Pessoa editor) {
        Professor professor = professorRepository.findByCpf(cpf).orElseThrow();
        ValidacaoEditorUtil.validarDesativacao(editor, professor);
        return professorRepository.save(professor);
    }

    @Override
    public Professor ativar(String cpf, Pessoa editor) {
        Professor professor = professorRepository.findByCpf(cpf).orElseThrow();
        ValidacaoEditorUtil.validarAtivacao(editor, professor);
        return professorRepository.save(professor);
    }
    

}
