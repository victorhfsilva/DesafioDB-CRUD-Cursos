package com.db.crudcursosbackend.domain.cursos.servicos;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.interfaces.IRegistrarCursoService;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.repositorios.ProfessorRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrarCursoService implements IRegistrarCursoService {

    private CursoRepository cursoRepository;
    private ProfessorRepository professorRepository;

    @Override
    public Curso registrar(Curso curso, String cpfProfessor, Pessoa editor) {
        if (editor != null){
            curso.setAtivo(true);
            curso.setCriadoAs(LocalDateTime.now());
            curso.setCriadoPor(editor.getContato().getEmail());
        }
       
        Professor professorSalvo = professorRepository.findByCpf(cpfProfessor).orElseThrow();
        curso.setProfessor(professorSalvo);

        return cursoRepository.save(curso);
    }


}
