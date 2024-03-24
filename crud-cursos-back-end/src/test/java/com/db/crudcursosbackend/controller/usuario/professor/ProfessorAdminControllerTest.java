package com.db.crudcursosbackend.controller.usuario.professor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.ProfessorDTO;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.AtualizarProfessorDTO;
import com.db.crudcursosbackend.domain.usuario.professor.servicos.ProfessorService;
import com.db.crudcursosbackend.domain.cursos.dtos.CursoDTO;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.ContatoBuilder;
import com.db.crudcursosbackend.domain.usuario.contato.dtos.ContatoDTO;
import com.db.crudcursosbackend.domain.usuario.endereco.dtos.EnderecoDTO;
import com.db.crudcursosbackend.domain.usuario.estado.Estado;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.servicos.PessoaService;
import com.db.crudcursosbackend.domain.usuario.professor.ProfessorBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProfessorAdminControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ProfessorAdminController pessoaAdminController;

    @MockBean
    private ProfessorService professorService;

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
    void dadoUmUsuario_quandoDesativado_deveRetornarSeusDados() throws Exception{
        
        ContatoDTO contatoDTO = ContatoDTO.builder().email("nome@email.com")
                                                    .celular("123456789")
                                                    .build();

        EnderecoDTO enderecoDTO1 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 201")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        EnderecoDTO enderecoDTO2 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 204")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        List<EnderecoDTO> enderecosDTOs = List.of(enderecoDTO1, enderecoDTO2);

        CursoDTO cursoDTO1 = CursoDTO.builder().nome("Matemática") 
                                                .descricao("Matemática para todos")
                                                .cargaHoraria(300)
                                                .build();

        CursoDTO cursoDTO2 = CursoDTO.builder().nome("Geometria") 
                                                .descricao("Geometria inclusiva")
                                                .cargaHoraria(300)
                                                .build();

        List<CursoDTO> cursosDTOs = List.of(cursoDTO1, cursoDTO2); 

        ProfessorDTO professorDTO = ProfessorDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("L33tP@swd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .salario(22064.68)
                                                    .cursos(cursosDTOs)
                                                    .build();
        
        Professor professor = professorDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);

        ProfessorBuilder professorBuilder = new ProfessorBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();

        Contato contatoEditor = contatoBuilder.celular("123456789")
                                                .email("admin@db.com")
                                                .build();

        Professor editor  = professorBuilder.cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .contato(contatoEditor)
                                        .build();

        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);
        
        when(professorService.desativar(eq("73565638435"), any())).thenReturn(professor);

        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/professor/desativar/73565638435"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("73565638435"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmUsuario_quandoAtivado_deveRetornarSeusDados() throws Exception {
        
        ContatoDTO contatoDTO = ContatoDTO.builder().email("nome@email.com")
                                                    .celular("123456789")
                                                    .build();

        EnderecoDTO enderecoDTO1 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 201")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        EnderecoDTO enderecoDTO2 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 204")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        List<EnderecoDTO> enderecosDTOs = List.of(enderecoDTO1, enderecoDTO2);
        CursoDTO cursoDTO1 = CursoDTO.builder().nome("Matemática") 
                                                .descricao("Matemática para todos")
                                                .cargaHoraria(300)
                                                .build();

        CursoDTO cursoDTO2 = CursoDTO.builder().nome("Geometria") 
                                                .descricao("Geometria inclusiva")
                                                .cargaHoraria(300)
                                                .build();

        List<CursoDTO> cursosDTOs = List.of(cursoDTO1, cursoDTO2); 

        ProfessorDTO professorDTO = ProfessorDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("L33tP@swd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .salario(22064.68)
                                                    .cursos(cursosDTOs)
                                                    .build();
        
        Professor professor = professorDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);

        ProfessorBuilder pessoaBuilder = new ProfessorBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();

        Contato contatoEditor = contatoBuilder.celular("123456789")
                                                .email("admin@db.com")
                                                .build();

        Pessoa editor  = pessoaBuilder.cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .contato(contatoEditor)
                                        .build();


        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);
        
        when(professorService.ativar(eq("73565638435"), any())).thenReturn(professor);

        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/professor/ativar/73565638435")
                                                .header("Authorization", "Bearer tokenValido"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("73565638435"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmUsuario_quandoExcluido_deveRetornarSeusDados() throws Exception{
        
        ContatoDTO contatoDTO = ContatoDTO.builder().email("nome@email.com")
                                                    .celular("123456789")
                                                    .build();

        EnderecoDTO enderecoDTO1 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 201")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        EnderecoDTO enderecoDTO2 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 204")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        List<EnderecoDTO> enderecosDTOs = List.of(enderecoDTO1, enderecoDTO2);

        CursoDTO cursoDTO1 = CursoDTO.builder().nome("Matemática") 
                                                .descricao("Matemática para todos")
                                                .cargaHoraria(300)
                                                .build();

        CursoDTO cursoDTO2 = CursoDTO.builder().nome("Geometria") 
                                                .descricao("Geometria inclusiva")
                                                .cargaHoraria(300)
                                                .build();

        List<CursoDTO> cursosDTOs = List.of(cursoDTO1, cursoDTO2); 

        ProfessorDTO professorDTO = ProfessorDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("L33tP@swd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .salario(22064.68)
                                                    .cursos(cursosDTOs)
                                                    .build();
        
        Professor professor = professorDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);

        when(professorService.excluir("73565638435")).thenReturn(professor);

        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/professor/excluir/73565638435"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("73565638435"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmUsuario_quandoAtualizadoComAdminValido_deveRetornarDadosAtualizados() throws Exception {
        
        ContatoDTO contatoDTO = ContatoDTO.builder().email("nome@email.com")
                                                    .celular("123456789")
                                                    .build();

        AtualizarProfessorDTO professorDTO = AtualizarProfessorDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("L33tP@swd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .build();
        
        String pessoaJson = objectMapper.writeValueAsString(professorDTO);
        
        Professor professor = professorDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        
        ProfessorBuilder professorBuilder = new ProfessorBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();

        Contato contatoEditor = contatoBuilder.celular("123456789")
                                                .email("admin@db.com")
                                                .build();

        Pessoa editor  = professorBuilder.cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .contato(contatoEditor)
                                        .build();

        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);

        when(professorService.atualizar(eq("73565638435"), any(), any())).thenReturn(professor);

        mockMvc.perform(MockMvcRequestBuilders.put("/admin/professor/atualizar/73565638435")
                                                .contentType("application/json")
                                                .content(pessoaJson)
                                                .param("papel", "ADMIN"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("73565638435"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmUsuario_quandoAtualizadoComCpfInvalido_deveRetornarErro() throws Exception {
        
        ContatoDTO contatoDTO = ContatoDTO.builder().email("nome@email.com")
                                                    .celular("123456789")
                                                    .build();

        AtualizarProfessorDTO professorDTO = AtualizarProfessorDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638430")
                                                    .senha("L33tP@swd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .build();
        
        String pessoaJson = objectMapper.writeValueAsString(professorDTO);
        
        Professor professor = professorDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        ProfessorBuilder pessoaBuilder = new ProfessorBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();

        Contato contatoEditor = contatoBuilder.celular("123456789")
                                                .email("admin@db.com")
                                                .build();

        Pessoa editor  = pessoaBuilder.cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .contato(contatoEditor)
                                        .build();


        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);

        when(professorService.atualizar(eq("73565638435"), any(), any())).thenReturn(professor);

        mockMvc.perform(MockMvcRequestBuilders.put("/admin/professor/atualizar/73565638435")
                                                .contentType("application/json")
                                                .content(pessoaJson))
                                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmUsuario_quandoAtualizadoComSenhaFraca_deveRetornarErro() throws Exception {
        
        ContatoDTO contatoDTO = ContatoDTO.builder().email("nome@email.com")
                                                    .celular("123456789")
                                                    .build();
                                                    
        AtualizarProfessorDTO professorDTO = AtualizarProfessorDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("senha123")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .build();
        
        String professorJson = objectMapper.writeValueAsString(professorDTO);
        
        Professor professor = professorDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        
        ProfessorBuilder pessoaBuilder = new ProfessorBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();

        Contato contatoEditor = contatoBuilder.celular("123456789")
                                                .email("admin@db.com")
                                                .build();

        Pessoa editor  = pessoaBuilder.cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .contato(contatoEditor)
                                        .build();

        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);

        when(professorService.atualizar(eq("73565638435"), any(), any())).thenReturn(professor);

        mockMvc.perform(MockMvcRequestBuilders.put("/admin/professor/atualizar/73565638435")
                                                .contentType("application/json")
                                                .content(professorJson)
                                                .header("Authorization", "Bearer tokenValido"))
                                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
