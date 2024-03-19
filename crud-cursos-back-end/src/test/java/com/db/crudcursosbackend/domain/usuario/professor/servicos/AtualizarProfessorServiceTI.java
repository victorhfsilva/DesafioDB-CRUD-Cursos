package com.db.crudcursosbackend.domain.usuario.professor.servicos;

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
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.repositorios.ProfessorRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AtualizarProfessorServiceTI {
    
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AtualizarProfessorService atualizarProfessorService;

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
        Professor professor = professorRepository.findByCpf("11111111111").orElseThrow();

        Contato novoContato = contatoBuilder.celular("123457891")
                                            .email(professor.getContato().getEmail())
                                            .build();

        professor.setContato(novoContato);
        professor.setSobrenome("Miralles");
        professor.setSalario(34054.08);

        Professor professorAtualizado = atualizarProfessorService.atualizar("11111111111", professor, professor);

        assertEquals(novoContato.getCelular(), professorAtualizado.getContato().getCelular());
        assertEquals("Miralles", professorAtualizado.getSobrenome());
        assertEquals(34054.08, professorAtualizado.getSalario());
    }
}
