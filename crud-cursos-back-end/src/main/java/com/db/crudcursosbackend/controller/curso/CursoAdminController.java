package com.db.crudcursosbackend.controller.curso;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.dtos.CursoDTO;
import com.db.crudcursosbackend.domain.cursos.dtos.CursoRespostaDTO;
import com.db.crudcursosbackend.domain.cursos.dtos.RespostaAtualizarCursoDTO;
import com.db.crudcursosbackend.domain.cursos.interfaces.ICursoService;
import io.swagger.v3.oas.annotations.Operation;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.RespostaAtualizarProfessorDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/admin/curso")
@AllArgsConstructor
public class CursoAdminController {

    private ICursoService cursoService;
    private IPessoaService pessoaService;

    @PatchMapping("/desativar/{id}")
    @Operation(summary = "Desativa curso por Id.")
    public ResponseEntity<CursoRespostaDTO> desativar(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpfEditor = user.getUsername();
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Curso curso = cursoService.desativar(id, editor);
        CursoRespostaDTO resposta = new CursoRespostaDTO(curso);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
    
    @DeleteMapping("/excluir/{id}")
    @Operation(summary = "Exclui curso por Id.")
    public ResponseEntity<CursoRespostaDTO> excluir(@PathVariable("id") Long id){
        Curso curso = cursoService.excluir(id);
        CursoRespostaDTO resposta = new CursoRespostaDTO(curso);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PatchMapping("/ativar/{id}")
    @Operation(summary = "Ativa curso por Id.")
    public ResponseEntity<CursoRespostaDTO> ativar(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpfEditor = user.getUsername();
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Curso curso = cursoService.ativar(id, editor);
        CursoRespostaDTO resposta = new CursoRespostaDTO(curso);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualiza curso por Id.")
    public ResponseEntity<RespostaAtualizarCursoDTO> atualizar(@PathVariable("id") Long id,
                                                        @RequestBody @Valid CursoDTO cursoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpfEditor = user.getUsername();
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Curso novoCurso = cursoDTO.converterParaEntidade();
        Curso curso = cursoService.atualizar(id, novoCurso, editor);
        RespostaAtualizarCursoDTO resposta = new RespostaAtualizarCursoDTO(curso);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PatchMapping("/atualizar/{id}/professor/{cpf}")
    @Operation(summary = "Atualiza professor do curso.")
    public ResponseEntity<RespostaAtualizarProfessorDTO> atualizarProfessor(@PathVariable("id") Long id,
                                                        @PathVariable("cpf") String cpf) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpfEditor = user.getUsername();
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Curso curso = cursoService.atualizarProfessor(id, cpf, editor);
        Professor professor = curso.getProfessor();
        RespostaAtualizarProfessorDTO resposta = new RespostaAtualizarProfessorDTO(professor);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PatchMapping("/atualizar/{id}/remover/aluno/{cpf}")
    @Operation(summary = "Remove aluno de curso.")
    public ResponseEntity<CursoRespostaDTO> removerAluno(@PathVariable("id") Long id,
                                                        @PathVariable("cpf") String cpf) {
        Curso curso = cursoService.removerAluno(id, cpf);
        CursoRespostaDTO resposta = new CursoRespostaDTO(curso);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PatchMapping("/atualizar/{id}/adicionar/aluno/{cpf}")
    @Operation(summary = "Adiciona aluno a curso.")
    public ResponseEntity<CursoRespostaDTO> adicionarAluno(@PathVariable("id") Long id,
                                                        @PathVariable("cpf") String cpf) {
        Curso curso = cursoService.adicionarAluno(id, cpf);
        CursoRespostaDTO resposta = new CursoRespostaDTO(curso);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PatchMapping("/verificarCadastro/{id}/aluno/{cpf}")
    @Operation(summary = "Verifica se aluno está cadastrado em curso.")
    public ResponseEntity<Boolean> verificarCadastroAluno(@PathVariable("id") Long id,
                                                        @PathVariable("cpf") String cpf) {
        boolean resposta = cursoService.verificarAlunoCadastrado(cpf, id);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PostMapping("/registrar/{cpf}")
    @Operation(summary = "Registra curso.")
    public ResponseEntity<CursoRespostaDTO> registrar(@PathVariable("cpf") String cpf,
                                                        @RequestBody @Valid CursoDTO cursoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpfEditor = user.getUsername();
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Curso novoCurso = cursoDTO.converterParaEntidade();
        Curso curso = cursoService.registrar(novoCurso, cpf, editor);
        CursoRespostaDTO resposta = new CursoRespostaDTO(curso);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
