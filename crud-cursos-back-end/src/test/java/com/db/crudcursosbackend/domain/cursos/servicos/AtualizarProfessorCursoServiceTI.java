package com.db.crudcursosbackend.domain.cursos.servicos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.repositorios.ProfessorRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AtualizarProfessorCursoServiceTI {
    
    @Autowired
    private AtualizarProfessorCursoService atualizarProfessorCursoService;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfessorRepository professorRepository;


    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadoUmCursoValidoSalvaNoBancoDeDados_QuandoCursoEhAtualizado_DeveRetornarCursoCorretoPorId(){
        cursoRepository.findById(1L).orElseThrow();
        
        Professor editor = professorRepository.findByCpf("11111111111").orElseThrow();

        Curso cursoAtualizado = atualizarProfessorCursoService.atualizar(1L, "44444444444", editor);

        assertEquals("44444444444", cursoAtualizado.getProfessor().getCpf());
        assertEquals("Dois", cursoAtualizado.getProfessor().getSobrenome());
    }

}
