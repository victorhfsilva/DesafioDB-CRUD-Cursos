package com.db.crudcursosbackend.domain.usuario.endereco.servicos;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudcursosbackend.domain.usuario.estado.Estado;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.repositorio.PessoaRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AdicionarEnderecoServiceTI {
    
    @Autowired
    private AdicionarEnderecoService adicionarEnderecoService;

    @Autowired
    private PessoaRepository pessoaRepository;

    private EnderecoBuilder enderecoBuilder;

    @BeforeEach 
    private void configurar() {
        enderecoBuilder = new EnderecoBuilder();
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmEnderecoValido_QuandoSalvaNoBancoDeDados_DeveSerAssociadoAPessoaCorreta() {
        Pessoa pessoaSalva = pessoaRepository.findByCpf("22222222222")
                                        .orElseThrow();

        Endereco endereco = enderecoBuilder.numero("12-a")
                                .rua("Rua A")
                                .bairro("Bairro A")
                                .cidade("Cidade A")
                                .estado(Estado.ACRE)
                                .cep("12345-758")
                                .pessoa(pessoaSalva)
                                .build();

        adicionarEnderecoService.adicionarEndereco(endereco);

        Pessoa pessoaComEndereco = pessoaRepository.findByCpf("22222222222")
                                            .orElseThrow();

        assertFalse(pessoaComEndereco.getEnderecos().stream()
                                .filter(e -> e.getNumero().equals("12-a"))
                                .toList().isEmpty());
    }
}
