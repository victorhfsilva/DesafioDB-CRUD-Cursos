package com.db.crudcursosbackend.controller.usuario.aluno;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
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
import java.util.List;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.dtos.AlunoDTO;
import com.db.crudcursosbackend.domain.usuario.aluno.servicos.AlunoService;
import com.db.crudcursosbackend.domain.usuario.contato.dtos.ContatoDTO;
import com.db.crudcursosbackend.domain.usuario.endereco.dtos.EnderecoDTO;
import com.db.crudcursosbackend.domain.usuario.estado.Estado;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.db.crudcursosbackend.domain.usuario.pessoa.servicos.PessoaService;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.ProfessorBuilder;
import com.db.crudcursosbackend.infra.seguranca.servicos.TokenService;
import com.db.crudcursosbackend.infra.seguranca.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegistrarAlunoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private RegistrarAlunoController registrarController;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AlunoService alunoService;

    @MockBean
    private PessoaService pessoaService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private TokenUtils tokenUtils;

    private ObjectMapper objectMapper;

    @BeforeEach
    void configuracao(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void dadoUmAlunoValido_quandoRegistrado_DeveRetornarOk() throws Exception{
    
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

        AlunoDTO alunoDTO = AlunoDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("L33tP@swd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String alunoJson = objectMapper.writeValueAsString(alunoDTO);

        when(tokenService.gerarToken("73565638435")).thenReturn("tokenValido");
        when(alunoService.registrar(any(), any())).thenReturn(alunoDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar/aluno/usuario")
                                                .contentType("application/json")
                                                .content(alunoJson))
                                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("tokenValido"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.pessoa.cpf").value(alunoDTO.getCpf()));
    }

    @Test
    void dadoUmUsuarioComCpfInvalido_quandoRegistrado_DeveRetornarErro() throws Exception{
    
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

        AlunoDTO alunoDTO = AlunoDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638430")
                                                    .senha("L33tP@wd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String alunoJson = objectMapper.writeValueAsString(alunoDTO);

        when(tokenService.gerarToken("73565638430")).thenReturn("tokenValido");
        when(alunoService.registrar(any(), any())).thenReturn(alunoDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar/aluno/usuario")
                                                .contentType("application/json")
                                                .content(alunoJson))
                                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void dadoUmUsuarioComSenhaFraca_quandoRegistrado_DeveRetornarErro() throws Exception{
    
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

        AlunoDTO alunoDTO = AlunoDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("senha123")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String alunoJson = objectMapper.writeValueAsString(alunoDTO);

        when(tokenService.gerarToken("73565638435")).thenReturn("tokenValido");
        when(alunoService.registrar(any(), any())).thenReturn(alunoDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar/aluno/usuario")
                                                .contentType("application/json")
                                                .content(alunoJson))
                                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmAdminValido_quandoRegistrado_DeveRetornarOk() throws Exception{
    
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

        AlunoDTO alunoDTO = AlunoDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("L33tP@wd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String alunoJson = objectMapper.writeValueAsString(alunoDTO);

        Aluno aluno = alunoDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        
        ProfessorBuilder pessoaBuilder = new ProfessorBuilder();
        Professor editor  = pessoaBuilder.cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .build();

        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);

        when(tokenService.gerarToken("73565638435")).thenReturn("outroTokenValido");
        when(alunoService.registrar(any(), any())).thenReturn(aluno);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar/aluno/admin")
                                                .contentType("application/json")
                                                .content(alunoJson))
                                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("outroTokenValido"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.pessoa.cpf").value(alunoDTO.getCpf()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmAdminComCpfInvalido_quandoRegistrado_DeveRetornarErro() throws Exception{
    
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

        AlunoDTO alunoDTO = AlunoDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638430")
                                                    .senha("L33tP@wd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String alunoJson = objectMapper.writeValueAsString(alunoDTO);

        Aluno aluno = alunoDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        
        ProfessorBuilder pessoaBuilder = new ProfessorBuilder();
        Professor editor  = pessoaBuilder.cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .build();

        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);

        when(tokenService.gerarToken("73565638430")).thenReturn("outroTokenValido");
        when(alunoService.registrar(any(), any())).thenReturn(aluno);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar/aluno/admin")
                                                .contentType("application/json")
                                                .content(alunoJson))
                                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmAdminComSenhaFraca_quandoRegistrado_DeveRetornarErro() throws Exception{
    
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

        AlunoDTO alunoDTO = AlunoDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("senha123")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String alunoJson = objectMapper.writeValueAsString(alunoDTO);

        Aluno aluno = alunoDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        
        ProfessorBuilder pessoaBuilder = new ProfessorBuilder();
        Professor editor  = pessoaBuilder.cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .build();

        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);

        when(tokenService.gerarToken("73565638435")).thenReturn("outroTokenValido");
        when(alunoService.registrar(any(), any())).thenReturn(aluno);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar/aluno/admin")
                                                .contentType("application/json")
                                                .content(alunoJson))
                                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
