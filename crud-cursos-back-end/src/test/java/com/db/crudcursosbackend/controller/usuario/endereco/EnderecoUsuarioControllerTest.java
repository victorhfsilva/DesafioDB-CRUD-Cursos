package com.db.crudcursosbackend.controller.usuario.endereco;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.List;
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
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.ContatoBuilder;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudcursosbackend.domain.usuario.endereco.dtos.EnderecoDTO;
import com.db.crudcursosbackend.domain.usuario.endereco.servicos.EnderecoService;
import com.db.crudcursosbackend.domain.usuario.endereco.utils.EnderecoUtils;
import com.db.crudcursosbackend.domain.usuario.estado.Estado;
import com.db.crudcursosbackend.domain.usuario.pessoa.servicos.PessoaService;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.ProfessorBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EnderecoUsuarioControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private EnderecoUsuarioController enderecoUsuarioController;

    @MockBean
    private PessoaService pessoaService;

    @MockBean
    private EnderecoService enderecoService;

    @MockBean
    private EnderecoUtils enderecoUtils;

    private ObjectMapper objectMapper;

    @BeforeEach
    void configuracao(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @WithMockUser(username = "73565638435", authorities = {"USUARIO"})
    void dadoUmUsuarioSalvo_quandoAdicionadoUmEndereco_deveRetornarEndereco() throws Exception{
       
        ProfessorBuilder professorBuilder = new ProfessorBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();
       
        Contato contato = contatoBuilder.celular("123456789")
                                        .email("pessoa@db.com")
                                        .build();

        Professor professor  = professorBuilder.nome("Nome")
                                        .sobrenome("Sobrenome")
                                        .cpf("73565638435")
                                        .senha("L33tP@swd")
                                        .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                        .contato(contato)
                                        .enderecos(List.of())
                                        .build();

        when(pessoaService.buscarPorCpf("73565638435")).thenReturn(professor);
    
        EnderecoDTO enderecoDTO = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 204")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        String enderecoJson = objectMapper.writeValueAsString(enderecoDTO);

        Endereco endereco = enderecoDTO.converterParaEntidade();
        
        when(enderecoService.adicionar(any())).thenReturn(endereco);

        mockMvc.perform(MockMvcRequestBuilders.post("/usuario/endereco/adicionar")
                                        .contentType("application/json")
                                        .content(enderecoJson)
                                        .header("Authorization", "Bearer tokenValido"))
                                        .andExpect(MockMvcResultMatchers.status().isCreated())
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.numero").value("126"))
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.cep").value("1234567"));
    }

    @Test
    @WithMockUser(username = "73565638435", authorities = {"USUARIO"})
    void dadoUmUsuarioSalvo_quandoDeletadoUmEndereco_deveRetornarEndereco() throws Exception{
       
        ProfessorBuilder professorBuilder = new ProfessorBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();
        EnderecoBuilder enderecoBuilder = new EnderecoBuilder();

        Endereco endereco = enderecoBuilder.numero("126")
                                            .complemento("Ap. 204")
                                            .rua("Rua")
                                            .bairro("Bairro")
                                            .cidade("Salvador")
                                            .estado(Estado.BAHIA)
                                            .cep("1234567")
                                            .build();

        Contato contato = contatoBuilder.celular("123456789")
                                        .email("pessoa@db.com")
                                        .build();

        Professor professor  = professorBuilder.nome("Nome")
                                        .sobrenome("Sobrenome")
                                        .cpf("73565638435")
                                        .senha("L33tP@swd")
                                        .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                        .contato(contato)
                                        .enderecos(List.of(endereco))
                                        .build();

        when(enderecoUtils.validarPermissaoDeAlterarEndereco("73565638435", 1L)).thenReturn(professor);
        when(pessoaService.atualizar(eq("73565638435"), any(), any())).thenReturn(professor);

        when(enderecoService.excluir(1L)).thenReturn(endereco);

        mockMvc.perform(MockMvcRequestBuilders.delete("/usuario/endereco/excluir/1")
                                        .header("Authorization", "Bearer tokenValido"))
                                        .andExpect(MockMvcResultMatchers.status().isOk())
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.numero").value("126"))
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.cep").value("1234567"));
    }

    @Test
    @WithMockUser(username = "73565638435", authorities = {"USUARIO"})
    void dadoUmUsuarioSalvo_quandoAtualizaUmEndereco_deveRetornarEnderecoCorreto() throws Exception{
       
        ProfessorBuilder pessoaBuilder = new ProfessorBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();
        EnderecoBuilder enderecoBuilder = new EnderecoBuilder();

        Endereco endereco = enderecoBuilder.numero("126")
                                            .complemento("Ap. 204")
                                            .rua("Rua")
                                            .bairro("Bairro")
                                            .cidade("Salvador")
                                            .estado(Estado.BAHIA)
                                            .cep("1234567")
                                            .build();

        Contato contato = contatoBuilder.celular("123456789")
                                        .email("pessoa@db.com")
                                        .build();

        Professor professor  = pessoaBuilder.nome("Nome")
                                        .sobrenome("Sobrenome")
                                        .cpf("73565638435")
                                        .senha("L33tP@swd")
                                        .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                        .contato(contato)
                                        .enderecos(List.of(endereco))
                                        .build();

        when(enderecoUtils.validarPermissaoDeAlterarEndereco("73565638435", 1L)).thenReturn(professor);
        when(pessoaService.atualizar(eq(professor.getCpf()),any(),any())).thenReturn(professor);

        EnderecoDTO enderecoDTO = EnderecoDTO.builder().numero("128")
                                                        .complemento("Casa")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        String enderecoJson = objectMapper.writeValueAsString(enderecoDTO);

        Endereco novoEndereco = enderecoDTO.converterParaEntidade();
        
        when(enderecoService.atualizar(eq(1L),any())).thenReturn(novoEndereco);

        mockMvc.perform(MockMvcRequestBuilders.put("/usuario/endereco/atualizar/1")
                                        .contentType("application/json")
                                        .content(enderecoJson)
                                        .header("Authorization", "Bearer tokenValido"))
                                        .andExpect(MockMvcResultMatchers.status().isOk())
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.numero").value("128"))
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.cep").value("1234567"));
    }
}
