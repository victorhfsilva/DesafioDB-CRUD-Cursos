package com.db.crudcursosbackend.domain.usuario.professor.servicos;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

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
class ExcluirProfessorServiceTI {
    
    @Autowired
    private ExcluirProfessorService excluirService;

    @Autowired
    private ProfessorRepository professorRepository;

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmaPessoaValidaSalvaNoBancoDeDados_QuandoExcluida_NaoDeveEncontrarEntidadeNoBancoDeDados(){
        
        excluirService.excluir("11111111111");
        
        assertFalse(professorRepository.findByCpf("11111111111").isPresent());
    }
}
