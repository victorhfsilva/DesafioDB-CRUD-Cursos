package com.db.crudcursosbackend.domain.usuario.endereco.servicos;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.db.crudcursosbackend.domain.usuario.aluno.repositorios.AlunoRepository;
import com.db.crudcursosbackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;

@SpringBootTest
@ActiveProfiles("test")
class ExcluirEnderecoServiceTI {
        
    @Autowired
    private ExcluirEnderecoService excluirService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void dadaUmEnderecoValidoSalvoNoBancoDeDados_QuandoExcluido_NaoDeveEncontrarEntidadeNoBancoDeDados(){
        
        Pessoa pessoa = alunoRepository.findByRua("Rua B").get(0);
        Long id = pessoa.getEnderecos().get(0).getId();

        excluirService.excluir(id);
        
        assertFalse(enderecoRepository.findById(id).isPresent());
        
    }
}
