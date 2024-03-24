package com.db.crudcursosbackend.controller.usuario.professor;

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
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.ProfessorRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.AtualizarProfessorDTO;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.RespostaAtualizarProfessorDTO;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IProfessorService;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IPessoaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/admin/professor")
@AllArgsConstructor
public class ProfessorAdminController {

    private IProfessorService professorService;
    private IPessoaService pessoaService;
    private PasswordEncoder passwordEncoder;


    @PatchMapping("/desativar/{cpf}")
    public ResponseEntity<ProfessorRespostaDTO> desativar(@PathVariable("cpf") String cpf) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpfEditor = user.getUsername();
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Professor professor = professorService.desativar(cpf, editor);
        ProfessorRespostaDTO resposta = new ProfessorRespostaDTO(professor);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
    
    @DeleteMapping("/excluir/{cpf}")
    public ResponseEntity<ProfessorRespostaDTO> excluir(@PathVariable("cpf") String cpf){
        Professor professor = professorService.excluir(cpf);
        ProfessorRespostaDTO resposta = new ProfessorRespostaDTO(professor);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PatchMapping("/ativar/{cpf}")
    public ResponseEntity<ProfessorRespostaDTO> ativar(@PathVariable("cpf") String cpf){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpfEditor = user.getUsername();
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Professor professor = professorService.ativar(cpf, editor);
        ProfessorRespostaDTO resposta = new ProfessorRespostaDTO(professor);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/atualizar/{cpf}")
    public ResponseEntity<RespostaAtualizarProfessorDTO> atualizar(@PathVariable("cpf") String cpf,
                                                        @RequestParam(name = "papel", defaultValue = "USUARIO") Papel papel, 
                                                        @RequestBody @Valid AtualizarProfessorDTO professorDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpfEditor = user.getUsername();
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Professor novoProfessor = professorDTO.converterParaEntidade(passwordEncoder, papel);
        Professor pessoa = professorService.atualizar(cpf, novoProfessor, editor);
        RespostaAtualizarProfessorDTO resposta = new RespostaAtualizarProfessorDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
