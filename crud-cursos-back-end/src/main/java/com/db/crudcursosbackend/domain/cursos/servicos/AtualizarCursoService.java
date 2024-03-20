package com.db.crudcursosbackend.domain.cursos.servicos;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.interfaces.IAtualizarCursoService;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;

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
        atualizarProfessor(cursoSalvo, novocurso);

        if (editor != null){
            cursoSalvo.setAtualizadoAs(LocalDateTime.now());
            cursoSalvo.setAtualizadoPor(editor.getContato().getEmail());
        }

        return cursoRepository.save(cursoSalvo);
    }
    
    private void atualizarProfessor(Curso cursoSalvo, Curso novoCurso) {

    }
}
