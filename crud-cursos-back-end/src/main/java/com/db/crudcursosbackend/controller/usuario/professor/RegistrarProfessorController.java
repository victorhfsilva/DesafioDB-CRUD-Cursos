package com.db.crudcursosbackend.controller.usuario.professor;

import org.springframework.web.bind.annotation.RestController;

import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.ProfessorDTO;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.ProfessorRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.RespostaRegistrarProfessorDTO;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IProfessorService;
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
@RequestMapping(value = "/registrar/professor")
@AllArgsConstructor
public class RegistrarProfessorController {
    
    private PasswordEncoder passwordEncoder;
    private IProfessorService professorService;
    private IPessoaService pessoaService;
    private ITokenService tokenService;

    @PostMapping("/usuario")
    @Operation(summary = "Registra novo professor com permissões de usuário.")
    public ResponseEntity<RespostaRegistrarProfessorDTO> registrarUsuario(@RequestBody @Valid ProfessorDTO professorDTO) {
        Professor professor = professorDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        Professor professorSalvo = professorService.registrar(professor, professor);
        String token = tokenService.gerarToken(professor.getCpf());
        ProfessorRespostaDTO pessoaRespostaDTO = new ProfessorRespostaDTO(professorSalvo);
        RespostaRegistrarProfessorDTO resposta = new RespostaRegistrarProfessorDTO(token, pessoaRespostaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PostMapping("/admin")
    @Operation(summary = "Registra novo professor com permissões de administrador.")
    public ResponseEntity<RespostaRegistrarProfessorDTO> registrarAdmin(@RequestBody @Valid ProfessorDTO professorDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Pessoa editor = pessoaService.buscarPorCpf(user.getUsername());   
        Professor professor = professorDTO.converterParaEntidade(passwordEncoder, Papel.ADMIN);
        Professor professorSalvo = professorService.registrar(professor, editor);
        String tokenGerado = tokenService.gerarToken(professor.getCpf());
        ProfessorRespostaDTO pessoaRespostaDTO = new ProfessorRespostaDTO(professorSalvo);
        RespostaRegistrarProfessorDTO resposta = new RespostaRegistrarProfessorDTO(tokenGerado, pessoaRespostaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }
    
}
