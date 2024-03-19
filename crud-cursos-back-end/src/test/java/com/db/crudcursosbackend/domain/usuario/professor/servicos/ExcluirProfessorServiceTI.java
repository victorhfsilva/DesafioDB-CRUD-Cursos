package com.db.crudcursosbackend.domain.usuario.professor.servicos;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
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
    void dadaUmProfessorValidoSalvaNoBancoDeDados_QuandoExcluida_NaoDeveEncontrarEntidadeNoBancoDeDados(){
        
        excluirService.excluir("11111111111");
        
        assertFalse(professorRepository.findByCpf("11111111111").isPresent());
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmAlunoValidaSalvaNoBancoDeDados_QuandoExcluida_DeveLancarExcecao(){
        
        assertThrows(NoSuchElementException.class, () -> {
            excluirService.excluir("22222222222");
        });
    }
}
