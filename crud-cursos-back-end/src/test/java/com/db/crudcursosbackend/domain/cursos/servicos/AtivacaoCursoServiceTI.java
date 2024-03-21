package com.db.crudcursosbackend.domain.cursos.servicos;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
class AtivacaoCursoServiceTI {

    @Autowired
    private AtivacaoCursoService ativacaoService;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfessorRepository professorRepository;
    
    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmaCursoValidaSalvaNoBancoDeDados_QuandoDesativada_DeveRetornarAEntidadeDesativada(){
        Professor editor = professorRepository.findByCpf("11111111111").orElseThrow();
        ativacaoService.desativar(1L, editor);
        
        assertFalse(cursoRepository.findById(1L).get().isAtivo());
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmaCursoValidaSalvaNoBancoDeDados_QuandoAtivada_DeveRetornarAEntidadeAtivada(){
        Professor editor = professorRepository.findByCpf("11111111111").orElseThrow();
        
        ativacaoService.desativar(1L, editor);
        ativacaoService.ativar(1L, editor);
        
        assertTrue(cursoRepository.findById(1L).get().isAtivo());
    }
}
