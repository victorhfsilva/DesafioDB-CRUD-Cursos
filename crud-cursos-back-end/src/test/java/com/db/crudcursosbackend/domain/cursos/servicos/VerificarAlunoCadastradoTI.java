package com.db.crudcursosbackend.domain.cursos.servicos;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

@SpringBootTest
@ActiveProfiles("test")
public class VerificarAlunoCadastradoTI {
    
    @Autowired
    private VerificarAlunoCadastrado verificarAlunoCadastrado;

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmAlunoCadastradoEmUmCurso_QuandoVerificadoSeCadastrado_DeveRetornarVerdadeiro(){
        boolean alunoPresente = verificarAlunoCadastrado.verficar("22222222222", 1L);
        assertTrue(alunoPresente);
    }


    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmAlunoNaoCadastradoEmUmCurso_QuandoVerificadoSeCadastrado_DeveRetornarFalso(){
        boolean alunoPresente = verificarAlunoCadastrado.verficar("22222222222", 2L);
        assertFalse(alunoPresente);
    }
}
