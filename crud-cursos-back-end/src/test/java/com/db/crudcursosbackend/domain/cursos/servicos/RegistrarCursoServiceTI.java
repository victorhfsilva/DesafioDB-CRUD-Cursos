package com.db.crudcursosbackend.domain.cursos.servicos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.CursoBuilder;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.repositorios.ProfessorRepository;

@SpringBootTest
@ActiveProfiles("test")
public class RegistrarCursoServiceTI {
    
    @Autowired
    private RegistrarCursoService registrarCursoService;

    @Autowired
    private ProfessorRepository professorRepository;

    private CursoBuilder cursoBuilder;

    @BeforeEach 
    private void configurar() {
        cursoBuilder = new CursoBuilder();
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados-sem-cursos.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmCursoValido_QuandoRegistrado_DeveRetornarCursoCorreto() {

        Curso curso = cursoBuilder.descricao("Matemática para todos.")
                                    .cargaHoraria(300)
                                    .nome("Matemática")
                                    .build();

        Professor editor = professorRepository.findByCpf("11111111111").orElseThrow();

        Curso cursoSalvo = registrarCursoService.registrar(curso, "11111111111", editor);

        assertEquals(curso.getNome(), cursoSalvo.getNome());
        assertEquals(curso.getProfessor().getCpf(), cursoSalvo.getProfessor().getCpf());
    }

}
