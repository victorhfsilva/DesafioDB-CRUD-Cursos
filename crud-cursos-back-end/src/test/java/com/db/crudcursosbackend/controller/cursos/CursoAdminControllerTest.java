package com.db.crudcursosbackend.controller.cursos;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.db.crudcursosbackend.controller.curso.CursoAdminController;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.dtos.CursoDTO;
import com.db.crudcursosbackend.domain.cursos.servicos.CursoService;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.ContatoBuilder;
import com.db.crudcursosbackend.domain.usuario.pessoa.servicos.PessoaService;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.ProfessorBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CursoAdminControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CursoAdminController cursoAdminController;

    @MockBean
    private CursoService cursoService;

    @MockBean
    private PessoaService pessoaService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void configuracao(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmCurso_quandoDesativado_deveRetornarSeusDados() throws Exception{
    
        ProfessorBuilder professorBuilder = new ProfessorBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();

        Contato contatoEditor = contatoBuilder.celular("123456789")
                                                .email("admin@db.com")
                                                .build();

        Professor editor  = professorBuilder.ativo(true)
                                        .cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .contato(contatoEditor)
                                        .build();

        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);

        CursoDTO cursoDTO = CursoDTO.builder().nome("Matemática") 
                                                .descricao("Matemática para todos")
                                                .cargaHoraria(300)
                                                .build();

        Curso curso = cursoDTO.converterParaEntidade();

        when(cursoService.desativar(eq(1L), eq(editor))).thenReturn(curso);

        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/curso/desativar/1"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Matemática"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value("300"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmCurso_quandoExcluido_deveRetornarSeusDados() throws Exception{
    
        CursoDTO cursoDTO = CursoDTO.builder().nome("Matemática") 
                                                .descricao("Matemática para todos")
                                                .cargaHoraria(300)
                                                .build();

        Curso curso = cursoDTO.converterParaEntidade();

        when(cursoService.excluir(1L)).thenReturn(curso);

        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/curso/excluir/1"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Matemática"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value("300"));
    }
}
