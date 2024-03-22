package com.db.crudcursosbackend.controller.usuario.endereco;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.dtos.EnderecoDTO;
import com.db.crudcursosbackend.domain.usuario.endereco.dtos.EnderecoRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.endereco.interfaces.IEnderecoService;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IPessoaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/admin/endereco")
@AllArgsConstructor
public class EnderecoAdminController {
    
    private IPessoaService pessoaService;
    private IEnderecoService enderecoService;

    @PostMapping("/adicionar/{cpf}")
    public ResponseEntity<EnderecoRespostaDTO> adicionarEndereco(
                @PathVariable("cpf") String cpf,
                @RequestBody @Valid EnderecoDTO enderecoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Pessoa editor = pessoaService.buscarPorCpf(user.getUsername());    
        Pessoa dono = pessoaService.buscarPorCpf(cpf);
        pessoaService.atualizar(dono.getCpf(), dono, editor);
        Endereco endereco = enderecoDTO.converterParaEntidadeComDono(dono);
        Endereco enderecoSalvo = enderecoService.adicionar(endereco);
        EnderecoRespostaDTO resposta = new EnderecoRespostaDTO(enderecoSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<EnderecoRespostaDTO> excluirEndereco(
                @PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Pessoa editor = pessoaService.buscarPorCpf(user.getUsername());        
        Pessoa dono = enderecoService.buscarEnderecoPorId(id).getPessoa();
        pessoaService.atualizar(dono.getCpf(), dono, editor);
        Endereco endereco = enderecoService.excluir(id);
        EnderecoRespostaDTO resposta = new EnderecoRespostaDTO(endereco);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<EnderecoRespostaDTO> atualizarEndereco(
                @PathVariable("id") Long id, 
                @RequestBody @Valid EnderecoDTO enderecoDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Pessoa editor = pessoaService.buscarPorCpf(user.getUsername());   
        Pessoa dono = enderecoService.buscarEnderecoPorId(id).getPessoa();
        pessoaService.atualizar(dono.getCpf(), dono, editor);
        Endereco novoEndereco = enderecoDTO.converterParaEntidadeComDono(dono);
        Endereco enderecoAtualizado = enderecoService.atualizar(id, novoEndereco);
        EnderecoRespostaDTO resposta = new EnderecoRespostaDTO(enderecoAtualizado);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

}
