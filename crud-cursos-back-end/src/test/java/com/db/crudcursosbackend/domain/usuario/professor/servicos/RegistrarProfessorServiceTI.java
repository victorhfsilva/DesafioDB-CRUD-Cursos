package com.db.crudcursosbackend.domain.usuario.professor.servicos;

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

import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.CursoBuilder;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.ContatoBuilder;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudcursosbackend.domain.usuario.estado.Estado;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.ProfessorBuilder;

@SpringBootTest
@ActiveProfiles("test")
public class RegistrarProfessorServiceTI {
    
    @Autowired
    private RegistrarProfessorService registrarProfessorService;

    private ContatoBuilder contatoBuilder;
    private EnderecoBuilder enderecoBuilder;
    private CursoBuilder cursoBuilder;
    private ProfessorBuilder professorBuilder;

    @BeforeEach 
    private void configurar() {
        contatoBuilder = new ContatoBuilder();
        enderecoBuilder = new EnderecoBuilder();
        cursoBuilder = new CursoBuilder();
        professorBuilder = new ProfessorBuilder();
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmProfessorValido_QuandoRegistrado_DeveRetornarProfessorCorreto() {
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

        Curso curso1 = cursoBuilder.cargaHoraria(300)
                                    .descricao("Matemática para todos.")
                                    .nome("Matemática")
                                    .build();
        
        Curso curso2 = cursoBuilder.cargaHoraria(50)
                                    .descricao("Redação para o ENEM.")
                                    .nome("Redação")
                                    .build();

        List<Curso> cursos = List.of(curso1, curso2);

        Professor professor = professorBuilder.nome("João")
                        .sobrenome("da Silva")
                        .cpf("223.356.7389-00")
                        .senha("senha123")
                        .papel(Papel.USUARIO)
                        .dataDeNascimento(LocalDate.of(1990, 5, 15))
                        .contato(contato)
                        .enderecos(enderecos)
                        .salario(22051.99)
                        .cursos(cursos)
                        .build();

        Professor professorSalvo = registrarProfessorService.registrar(professor, professor);

        assertEquals(professor.getCpf(), professorSalvo.getCpf());
    }
}
