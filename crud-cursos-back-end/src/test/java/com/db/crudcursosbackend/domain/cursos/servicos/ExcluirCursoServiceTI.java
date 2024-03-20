package com.db.crudcursosbackend.domain.cursos.servicos;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ExcluirCursoServiceTI {
    
    @Autowired
    private ExcluirCursoService excluirCursoService;

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmCursoComAlunos_QuandoExcluido_NaoDeveEncontrarEntidadeNoBancoDeDados(){
        
        excluirCursoService.excluir(1L);
        assertFalse(cursoRepository.findById(1L).isPresent());
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmCursoSemAlunos_QuandoExcluido_NaoDeveEncontrarEntidadeNoBancoDeDados(){
        
        excluirCursoService.excluir(3L);
        assertFalse(cursoRepository.findById(3L).isPresent());
    }
}
