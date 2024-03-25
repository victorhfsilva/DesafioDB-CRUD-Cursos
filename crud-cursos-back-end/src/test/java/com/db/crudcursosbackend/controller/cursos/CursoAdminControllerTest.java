package com.db.crudcursosbackend.controller.cursos;

import java.time.LocalDate;
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
import com.db.crudcursosbackend.domain.cursos.CursoBuilder;
import com.db.crudcursosbackend.domain.cursos.dtos.CursoDTO;
import com.db.crudcursosbackend.domain.cursos.servicos.CursoService;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.AlunoBuilder;
import com.db.crudcursosbackend.domain.usuario.aluno.dtos.AlunoDTO;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.ContatoBuilder;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudcursosbackend.domain.usuario.endereco.dtos.EnderecoDTO;
import com.db.crudcursosbackend.domain.usuario.estado.Estado;
import com.db.crudcursosbackend.domain.usuario.pessoa.servicos.PessoaService;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.ProfessorBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;

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
    void dadoUmCurso_quandoAtivado_deveRetornarSeusDados() throws Exception{
    
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

        when(cursoService.ativar(eq(1L), eq(editor))).thenReturn(curso);

        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/curso/ativar/1"))
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

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmCurso_quandoAtualizado_deveRetornarDadosAtualizados() throws Exception{
    
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

        String cursoJson = objectMapper.writeValueAsString(cursoDTO);
        Curso curso = cursoDTO.converterParaEntidade();

        when(cursoService.atualizar(1L, curso, editor)).thenReturn(curso);

        mockMvc.perform(MockMvcRequestBuilders.put("/admin/curso/atualizar/1")
                                    .contentType("application/json")
                                                .content(cursoJson))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Matemática"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value("300"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmCurso_quandoAtualizadoProfessor_deveRetornarProfessorAtualizados() throws Exception{
    
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
        
        Contato contatoProfessor = contatoBuilder.celular("1234453789")
                                                .email("professor@db.com")
                                                .build();

        Professor professor  = professorBuilder.ativo(true)
                                        .cpf("64174892563")
                                        .nome("professor")
                                        .senha("senha123")
                                        .contato(contatoProfessor)
                                        .build();

        CursoBuilder cursoBuilder = new CursoBuilder();

        Curso curso = cursoBuilder.nome("Matemática") 
                                            .descricao("Matemática para todos")
                                            .cargaHoraria(300)
                                            .professor(professor)
                                            .build();

        when(cursoService.atualizarProfessor(1L, "64174892563", editor)).thenReturn(curso);

        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/curso/atualizar/1/professor/64174892563"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("professor"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmCurso_quandoAlunoRemovido_deveRetornarCursoCorretamente() throws Exception{
    
        ProfessorBuilder professorBuilder = new ProfessorBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();
        EnderecoBuilder enderecoBuilder = new EnderecoBuilder();
        
        Contato contatoProfessor = contatoBuilder.celular("1234453789")
                                                .email("professor@db.com")
                                                .build();

        Endereco endereco1 = enderecoBuilder.numero("126")
                                                .complemento("Ap. 201")
                                                .rua("Rua")
                                                .bairro("Bairro")
                                                .cidade("Salvador")
                                                .estado(Estado.BAHIA)
                                                .cep("1234567")
                                                .build();

        Endereco endereco2 = enderecoBuilder.numero("126")
                                                .complemento("Ap. 204")
                                                .rua("Rua")
                                                .bairro("Bairro")
                                                .cidade("Salvador")
                                                .estado(Estado.BAHIA)
                                                .cep("1234567")
                                                .build();

        List<Endereco> enderecos = List.of(endereco1, endereco2);

        Professor professor  = professorBuilder.ativo(true)
                                        .cpf("64174892563")
                                        .nome("professor")
                                        .senha("senha123")
                                        .contato(contatoProfessor)
                                        .enderecos(enderecos)
                                        .build();

        CursoBuilder cursoBuilder = new CursoBuilder();

        Curso curso = cursoBuilder.nome("Matemática") 
                                            .descricao("Matemática para todos")
                                            .cargaHoraria(300)
                                            .professor(professor)
                                            .alunos(List.of())
                                            .build();

        when(cursoService.removerAluno(1L, "64174892563")).thenReturn(curso);

        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/curso/atualizar/1/remover/aluno/64174892563"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Matemática"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value("300"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmCurso_quandoAlunoAdicionado_deveRetornarCursoCorretamente() throws Exception{
    
        ProfessorBuilder professorBuilder = new ProfessorBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();
        EnderecoBuilder enderecoBuilder = new EnderecoBuilder();
        AlunoBuilder alunoBuilder = new AlunoBuilder();
        
        Contato contatoProfessor = contatoBuilder.celular("1234453789")
                                                .email("professor@db.com")
                                                .build();

        Endereco endereco1 = enderecoBuilder.numero("126")
                                                .complemento("Ap. 201")
                                                .rua("Rua")
                                                .bairro("Bairro")
                                                .cidade("Salvador")
                                                .estado(Estado.BAHIA)
                                                .cep("1234567")
                                                .build();

        Endereco endereco2 = enderecoBuilder.reset().numero("126")
                                                .complemento("Ap. 204")
                                                .rua("Rua")
                                                .bairro("Bairro")
                                                .cidade("Salvador")
                                                .estado(Estado.BAHIA)
                                                .cep("1234567")
                                                .build();

        List<Endereco> enderecosProfessor = List.of(endereco1, endereco2);

        Professor professor  = professorBuilder.ativo(true)
                                        .cpf("64174892563")
                                        .nome("professor")
                                        .senha("senha123")
                                        .contato(contatoProfessor)
                                        .enderecos(enderecosProfessor)
                                        .build();


        Contato contatoAluno = contatoBuilder.celular("12342133789")
                                            .email("aluno@db.com")
                                            .build();

        Endereco endereco3 = enderecoBuilder.reset().numero("126")
                                                    .complemento("Ap. 204")
                                                    .rua("Rua")
                                                    .bairro("Bairro")
                                                    .cidade("Salvador")
                                                    .estado(Estado.BAHIA)
                                                    .cep("1234567")
                                                    .build();

        List<Endereco> enderecosAluno = List.of(endereco3);

        Aluno aluno = alunoBuilder.nome("Nome")
                                                .sobrenome("Sobrenome")
                                                .cpf("73565638435")
                                                .senha("L33tP@swd")
                                                .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                .contato(contatoAluno)
                                                .enderecos(enderecosAluno)
                                                .build();

        CursoBuilder cursoBuilder = new CursoBuilder();

        Curso curso = cursoBuilder.nome("Matemática") 
                                            .descricao("Matemática para todos")
                                            .cargaHoraria(300)
                                            .professor(professor)
                                            .alunos(List.of(aluno))
                                            .build();

        when(cursoService.adicionarAluno(1L, "64174892563")).thenReturn(curso);

        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/curso/atualizar/1/adicionar/aluno/64174892563"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Matemática"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value("300"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmCurso_quandoRegistrado_deveRetornarDadosAtualizados() throws Exception{
    
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

        String cursoJson = objectMapper.writeValueAsString(cursoDTO);
        Curso curso = cursoDTO.converterParaEntidade();

        when(cursoService.registrar(curso,  "73565638435", editor)).thenReturn(curso);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/curso/registrar/73565638435")
                                                .contentType("application/json")
                                                .content(cursoJson))
                                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Matemática"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value("300"));
    }
}
