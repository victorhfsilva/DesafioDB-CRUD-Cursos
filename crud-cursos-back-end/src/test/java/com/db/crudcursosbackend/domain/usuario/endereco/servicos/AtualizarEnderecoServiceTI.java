package com.db.crudcursosbackend.domain.usuario.endereco.servicos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import java.time.LocalDate;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudcursosbackend.domain.usuario.estado.Estado;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.repositorio.PessoaRepository;
import com.db.crudcursosbackend.domain.usuario.aluno.AlunoBuilder;
import com.db.crudcursosbackend.domain.usuario.aluno.repositorios.AlunoRepository;

@SpringBootTest
@ActiveProfiles("test")
class AtualizarEnderecoServiceTI {
    
    @Autowired
    private AtualizarEnderecoService atualizarEnderecoService;

    @Autowired
    private AlunoRepository alunoRepository;

    private EnderecoBuilder enderecoBuilder;

    @BeforeEach 
    private void configurar() {
        enderecoBuilder = new EnderecoBuilder();
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void dadaUmEnderecoValidoSalvo_QuandoAtualizado_DeveRetornarEnderecoCorreto() {
        
        Pessoa pessoa = alunoRepository.findByRua("Rua B").get(0);
        
        Endereco novoEndereco = enderecoBuilder.numero("12-a")
                                .rua("Rua B")
                                .bairro("Bairro A")
                                .cidade("Cidade A")
                                .estado(Estado.ACRE)
                                .cep("12345-758")
                                .pessoa(pessoa)
                                .build();
    
        Endereco enderecoSalvo = atualizarEnderecoService.atualizar(1L, novoEndereco);

        assertEquals(novoEndereco, enderecoSalvo);
        assertEquals(pessoa.getCpf(), enderecoSalvo.getPessoa().getCpf());
    }

}
