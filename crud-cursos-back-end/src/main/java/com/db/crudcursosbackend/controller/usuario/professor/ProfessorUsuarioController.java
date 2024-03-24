package com.db.crudcursosbackend.controller.usuario.professor;

import org.springframework.web.bind.annotation.RestController;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.AtualizarProfessorDTO;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.ProfessorRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.RespostaAtualizarProfessorDTO;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IProfessorService;

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
@RequestMapping(value = "/usuario/professor")
@AllArgsConstructor
public class ProfessorUsuarioController {
    
    private IProfessorService professorService;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/dados")
    @Operation(summary = "Obtém os dados do professor logado.")
    public ResponseEntity<ProfessorRespostaDTO> getDados() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpf = user.getUsername();
        Professor professor = professorService.buscarPorCpf(cpf);
        ProfessorRespostaDTO resposta = new ProfessorRespostaDTO(professor);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PatchMapping("/desativar")
    @Operation(summary = "Desativa o professor logado.")
    public ResponseEntity<ProfessorRespostaDTO> desativar(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpf = user.getUsername();
        Professor professorSalvo = professorService.buscarPorCpf(cpf);
        Professor professor = professorService.desativar(cpf, professorSalvo);
        ProfessorRespostaDTO resposta = new ProfessorRespostaDTO(professor);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
    
    @PatchMapping("/ativar")
    @Operation(summary = "Ativa o professor logado.")
    public ResponseEntity<ProfessorRespostaDTO> ativar(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpf = user.getUsername();
        Professor pessoaSalva = professorService.buscarPorCpf(cpf);
        Professor professor = professorService.ativar(cpf, pessoaSalva);
        ProfessorRespostaDTO resposta = new ProfessorRespostaDTO(professor);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualiza o professor logado.")
    public ResponseEntity<RespostaAtualizarProfessorDTO> atualizar(@RequestBody @Valid AtualizarProfessorDTO professorDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpf = user.getUsername();
        Professor antigoProfessor = professorService.buscarPorCpf(cpf);
        Professor novoProfessor = professorDTO.converterParaEntidade(passwordEncoder, antigoProfessor.getPapel());
        Professor professor = professorService.atualizar(cpf, novoProfessor, antigoProfessor);
        RespostaAtualizarProfessorDTO resposta = new RespostaAtualizarProfessorDTO(professor);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
