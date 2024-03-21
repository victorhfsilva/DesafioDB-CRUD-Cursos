package com.db.crudcursosbackend.domain.cursos.servicos;


import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

@SpringBootTest
@ActiveProfiles("test")
public class RemoverAlunoServiceTI {
    
    @Autowired
    private RemoverAlunoService removerAlunoService;

    @Autowired
    private VerificarAlunoCadastradoService verificarAlunoCadastrado;

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmCursoValidoSalvoNoBancoDeDados_QuandoAlunoAdicionado_DeveRetornarAlunoAdicionado(){
        removerAlunoService.remover(1L, "22222222222");
        
        assertFalse(verificarAlunoCadastrado.verficar("22222222222", 1L));
    }
}
