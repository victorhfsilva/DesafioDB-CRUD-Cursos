package com.db.crudcursosbackend.controller.usuario.aluno;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.dtos.AlunoRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.aluno.dtos.AtualizarAlunoDTO;
import com.db.crudcursosbackend.domain.usuario.aluno.dtos.RespostaAtualizarAlunoDTO;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IAlunoService;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IPessoaService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/admin/aluno")
@AllArgsConstructor
public class AlunoAdminController {

    private IAlunoService alunoService;
    private IPessoaService pessoaService;
    private PasswordEncoder passwordEncoder;


    @PatchMapping("/desativar/{cpf}")
    @Operation(summary = "Desativa um aluno por CPF.")
    public ResponseEntity<AlunoRespostaDTO> desativar(@PathVariable("cpf") String cpf){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpfEditor = user.getUsername();
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Aluno aluno = alunoService.desativar(cpf, editor);
        AlunoRespostaDTO resposta = new AlunoRespostaDTO(aluno);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
    
    @DeleteMapping("/excluir/{cpf}")
    @Operation(summary = "Exclui um aluno por CPF.")
    public ResponseEntity<AlunoRespostaDTO> excluir(@PathVariable("cpf") String cpf){
        Aluno aluno = alunoService.excluir(cpf);
        AlunoRespostaDTO resposta = new AlunoRespostaDTO(aluno);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PatchMapping("/ativar/{cpf}")
    @Operation(summary = "Ativa um aluno por cpf.")
    public ResponseEntity<AlunoRespostaDTO> ativar(@PathVariable("cpf") String cpf){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpfEditor = user.getUsername();
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Aluno aluno = alunoService.ativar(cpf, editor);
        AlunoRespostaDTO resposta = new AlunoRespostaDTO(aluno);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/atualizar/{cpf}")
    @Operation(summary = "Atualiza um aluno por cpf.")
    public ResponseEntity<RespostaAtualizarAlunoDTO> atualizar(@PathVariable("cpf") String cpf,
                                                        @RequestParam(name = "papel", defaultValue = "USUARIO") Papel papel, 
                                                        @RequestBody @Valid AtualizarAlunoDTO alunoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpfEditor = user.getUsername();
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Aluno novoAluno = alunoDTO.converterParaEntidade(passwordEncoder, papel);
        Aluno pessoa = alunoService.atualizar(cpf, novoAluno, editor);
        RespostaAtualizarAlunoDTO resposta = new RespostaAtualizarAlunoDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
