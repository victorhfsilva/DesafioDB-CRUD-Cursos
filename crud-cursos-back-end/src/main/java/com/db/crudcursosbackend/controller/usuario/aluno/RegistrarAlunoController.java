package com.db.crudcursosbackend.controller.usuario.aluno;

import org.springframework.web.bind.annotation.RestController;

import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.dtos.AlunoDTO;
import com.db.crudcursosbackend.domain.usuario.aluno.dtos.AlunoRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.aluno.dtos.RespostaRegistrarAlunoDTO;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IAlunoService;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudcursosbackend.infra.seguranca.interfaces.ITokenService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/registrar/aluno")
@AllArgsConstructor
public class RegistrarAlunoController {
    
    private PasswordEncoder passwordEncoder;
    private IAlunoService alunoService;
    private IPessoaService pessoaService;
    private ITokenService tokenService;

    @PostMapping("/usuario")
    @Operation(summary = "Registra novo aluno com permissões de usuário.")
    public ResponseEntity<RespostaRegistrarAlunoDTO> registrarUsuario(@RequestBody @Valid AlunoDTO alunoDTO) {
        Aluno aluno = alunoDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        Aluno alunoSalvo = alunoService.registrar(aluno, aluno);
        String token = tokenService.gerarToken(aluno.getCpf());
        AlunoRespostaDTO pessoaRespostaDTO = new AlunoRespostaDTO(alunoSalvo);
        RespostaRegistrarAlunoDTO resposta = new RespostaRegistrarAlunoDTO(token, pessoaRespostaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PostMapping("/admin")
    @Operation(summary = "Registra novo aluno com permissões de administrador.")
    public ResponseEntity<RespostaRegistrarAlunoDTO> registrarAdmin(@RequestBody @Valid AlunoDTO alunoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Pessoa editor = pessoaService.buscarPorCpf(user.getUsername());   
        Aluno aluno = alunoDTO.converterParaEntidade(passwordEncoder, Papel.ADMIN);
        Aluno alunoSalvo = alunoService.registrar(aluno, editor);
        String tokenGerado = tokenService.gerarToken(aluno.getCpf());
        AlunoRespostaDTO pessoaRespostaDTO = new AlunoRespostaDTO(alunoSalvo);
        RespostaRegistrarAlunoDTO resposta = new RespostaRegistrarAlunoDTO(tokenGerado, pessoaRespostaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }
    
}
