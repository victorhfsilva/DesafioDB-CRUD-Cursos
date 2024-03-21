package com.db.crudcursosbackend.domain.cursos.servicos;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.db.crudcursosbackend.domain.cursos.Curso;

@SpringBootTest
@ActiveProfiles("test")
public class AdicionarAlunoServiceTI {
    
    @Autowired
    private AdicionarAlunoService adicionarAlunoService;

    @Autowired
    private VerificarAlunoCadastrado verificarAlunoCadastrado;

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmCursoValidoSalvoNoBancoDeDados_QuandoAlunoAdicionado_DeveRetornarAlunoAdicionado(){
        adicionarAlunoService.adicionar(4L, "22222222222");
        
        assertTrue(verificarAlunoCadastrado.verficar("22222222222", 4L));
    }
}
