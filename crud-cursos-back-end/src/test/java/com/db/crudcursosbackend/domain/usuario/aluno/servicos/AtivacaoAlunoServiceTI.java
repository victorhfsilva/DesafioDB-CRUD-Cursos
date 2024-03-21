package com.db.crudcursosbackend.domain.usuario.aluno.servicos;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.repositorios.AlunoRepository;

@SpringBootTest
@ActiveProfiles("test")
class AtivacaoAlunoServiceTI {

    @Autowired
    private AtivacaoAlunoService ativacaoService;

    @Autowired
    private AlunoRepository alunoRepository;
    
    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmaAlunoValidaSalvaNoBancoDeDados_QuandoDesativada_DeveRetornarAEntidadeDesativada(){
        Aluno editor = alunoRepository.findByCpf("22222222222").orElseThrow();
        ativacaoService.desativar("22222222222", editor);
        
        assertFalse(alunoRepository.findByCpf("22222222222").get().isAtivo());
        
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmaAlunoValidaSalvaNoBancoDeDados_QuandoAtivada_DeveRetornarAEntidadeAtivada(){
        Aluno editor = alunoRepository.findByCpf("22222222222").orElseThrow();
        
        ativacaoService.desativar("22222222222", editor);

        editor = alunoRepository.findByCpf("22222222222").orElseThrow();
        ativacaoService.ativar("22222222222", editor);
        
        assertTrue(alunoRepository.findByCpf("22222222222").get().isAtivo());
        
    }
}
