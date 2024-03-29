package com.db.crudcursosbackend.domain.cursos.servicos;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.interfaces.IRegistrarCursoService;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.repositorios.ProfessorRepository;
import com.db.crudcursosbackend.infra.excecoes.EntidadeDesativada;
import com.db.crudcursosbackend.infra.validacoes.ValidacaoEditorUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrarCursoService implements IRegistrarCursoService {

    private CursoRepository cursoRepository;
    private ProfessorRepository professorRepository;

    @Override
    public Curso registrar(Curso curso, String cpfProfessor, Pessoa editor) {
        ValidacaoEditorUtil.validarRegistro(curso, editor);
       
        Professor professorSalvo = professorRepository.findByCpf(cpfProfessor).orElseThrow();
        
        if (professorSalvo.isAtivo()){
            curso.setProfessor(professorSalvo);
            return cursoRepository.save(curso);
        }
        throw new EntidadeDesativada("O professor com cpf " + cpfProfessor + " foi desativada.");
    }



}
