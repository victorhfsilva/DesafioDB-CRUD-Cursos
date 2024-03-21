package com.db.crudcursosbackend.domain.cursos.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.interfaces.IAtivacaoCursoService;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.infra.validacoes.ValidacaoEditorUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AtivacaoCursoService implements IAtivacaoCursoService {

    private CursoRepository cursoRepository;

    @Override
    public Curso desativar(Long id, Pessoa editor) {
        Curso curso = cursoRepository.findById(id).orElseThrow();
        ValidacaoEditorUtil.validarDesativacao(editor, curso);
        return cursoRepository.save(curso);
    }

    @Override
    public Curso ativar(Long id, Pessoa editor) {
        Curso curso = cursoRepository.findById(id).orElseThrow();
        ValidacaoEditorUtil.validarAtivacao(editor, curso);
        return cursoRepository.save(curso);
    }
    
}
