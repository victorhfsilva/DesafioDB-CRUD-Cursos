package com.db.crudcursosbackend.domain.usuario.aluno.servicos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.ContatoBuilder;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.repositorios.AlunoRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AtualizarAlunoServiceTI {
    
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AtualizarAlunoService atualizarAlunoService;

    private ContatoBuilder contatoBuilder;

    @BeforeEach 
    private void configurar() {
        contatoBuilder = new ContatoBuilder();
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmaPessoaValidaSalvaNoBancoDeDados_QuandoContatoEhAtualizado_DeveRetornarPessoaCorretaPorId(){
        Aluno aluno = alunoRepository.findByCpf("22222222222").orElseThrow();

        Contato novoContato = contatoBuilder.celular("123457891")
                                            .email(aluno.getContato().getEmail())
                                            .build();

        aluno.setContato(novoContato);
        aluno.setSobrenome("Miralles");
        aluno.setMatricula("A004");

        Aluno alunoAtualizado = atualizarAlunoService.atualizar("22222222222", aluno, aluno);

        assertEquals(novoContato.getCelular(), alunoAtualizado.getContato().getCelular());
        assertEquals("Miralles", alunoAtualizado.getSobrenome());
        assertEquals("A004", alunoAtualizado.getMatricula());
    }
}
