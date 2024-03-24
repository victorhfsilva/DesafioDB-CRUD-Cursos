package com.db.crudcursosbackend.controller.usuario.aluno;

import org.springframework.web.bind.annotation.RestController;
import com.db.crudcursosbackend.domain.usuario.aluno.dtos.AtualizarAlunoDTO;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.dtos.AlunoRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.aluno.dtos.RespostaAtualizarAlunoDTO;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IAlunoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/usuario/aluno")
@AllArgsConstructor
public class AlunoUsuarioController {
    
    private IAlunoService alunoService;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/dados")
    @Operation(summary = "Obtém os dados do aluno logado.")
    public ResponseEntity<AlunoRespostaDTO> getDados() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpf = user.getUsername();
        Aluno aluno = alunoService.buscarPorCpf(cpf);
        AlunoRespostaDTO resposta = new AlunoRespostaDTO(aluno);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PatchMapping("/desativar")
    @Operation(summary = "Desativa o aluno logado.")
    public ResponseEntity<AlunoRespostaDTO> desativar(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpf = user.getUsername();
        Aluno alunoSalvo = alunoService.buscarPorCpf(cpf);
        Aluno aluno = alunoService.desativar(cpf, alunoSalvo);
        AlunoRespostaDTO resposta = new AlunoRespostaDTO(aluno);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
    
    @PatchMapping("/ativar")
    @Operation(summary = "Ativa o aluno logado.")
    public ResponseEntity<AlunoRespostaDTO> ativar(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpf = user.getUsername();
        Aluno pessoaSalva = alunoService.buscarPorCpf(cpf);
        Aluno aluno = alunoService.ativar(cpf, pessoaSalva);
        AlunoRespostaDTO resposta = new AlunoRespostaDTO(aluno);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualiza o aluno logado.")
    public ResponseEntity<RespostaAtualizarAlunoDTO> atualizar(@RequestBody @Valid AtualizarAlunoDTO alunoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpf = user.getUsername();
        Aluno antigoAluno = alunoService.buscarPorCpf(cpf);
        Aluno novoAluno = alunoDTO.converterParaEntidade(passwordEncoder, antigoAluno.getPapel());
        Aluno aluno = alunoService.atualizar(cpf, novoAluno, novoAluno);
        RespostaAtualizarAlunoDTO resposta = new RespostaAtualizarAlunoDTO(aluno);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
