package com.db.crudcursosbackend.domain.usuario.aluno.servicos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.ContatoBuilder;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudcursosbackend.domain.usuario.estado.Estado;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.AlunoBuilder;

@SpringBootTest
@ActiveProfiles("test")
public class RegistrarAlunoServiceTI {
    
    @Autowired
    private RegistrarAlunoService registrarAlunoService;

    private ContatoBuilder contatoBuilder;
    private EnderecoBuilder enderecoBuilder;
    private AlunoBuilder alunoBuilder;

    @BeforeEach 
    private void configurar() {
        contatoBuilder = new ContatoBuilder();
        enderecoBuilder = new EnderecoBuilder();
        alunoBuilder = new AlunoBuilder();
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmAlunoValido_QuandoRegistrado_DeveRetornarAlunoCorreto() {
        Contato contato = contatoBuilder.celular("12345614567")
                                            .email("meu_email@email.com")
                                            .build();

        Endereco endereco1 = enderecoBuilder.numero("12-a")
                                        .rua("Rua A")
                                        .bairro("Bairro A")
                                        .cidade("Cidade A")
                                        .estado(Estado.ACRE)
                                        .cep("12345-758")
                                        .build();

        Endereco endereco2 = enderecoBuilder.reset()
                                .numero("14")
                                .rua("Rua B")
                                .bairro("Bairro B")
                                .cidade("Cidade B")
                                .estado(Estado.ALAGOAS)
                                .cep("97561-758")
                                .build();

        List<Endereco> enderecos = List.of(endereco1, endereco2);

        Aluno aluno = alunoBuilder.ativo(true)
                        .nome("João")
                        .sobrenome("da Silva")
                        .cpf("223.356.7389-00")
                        .senha("senha123")
                        .papel(Papel.USUARIO)
                        .dataDeNascimento(LocalDate.of(1990, 5, 15))
                        .contato(contato)
                        .enderecos(enderecos)
                        .matricula("A003")
                        .dataDeIngresso(LocalDate.now())
                        .build();

        Aluno alunoSalvo = registrarAlunoService.registrar(aluno, aluno);

        assertEquals(aluno.getCpf(), alunoSalvo.getCpf());
    }
}
