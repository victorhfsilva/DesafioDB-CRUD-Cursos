package com.db.crudcursosbackend.domain.cursos.servicos;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.interfaces.IAtualizarCursoService;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.infra.excecoes.EditorInvalido;
import com.db.crudcursosbackend.infra.validacoes.ValidacaoEditorUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AtualizarCursoService implements IAtualizarCursoService {

    private CursoRepository cursoRepository;

    @Override
    public Curso atualizar(Long id, Curso novocurso, Pessoa editor) {
        
        Curso cursoSalvo = cursoRepository.findById(id).orElseThrow();
        
        cursoSalvo.setCargaHoraria(novocurso.getCargaHoraria());
        cursoSalvo.setDescricao(novocurso.getDescricao());
        cursoSalvo.setNome(novocurso.getNome());

        ValidacaoEditorUtil.validarAtualizacao(editor, cursoSalvo);

        return cursoRepository.save(cursoSalvo);
    }
    
}
