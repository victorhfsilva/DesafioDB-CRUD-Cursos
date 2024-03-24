package com.db.crudcursosbackend.controller.usuario.endereco;

import org.springframework.web.bind.annotation.RestController;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.dtos.EnderecoDTO;
import com.db.crudcursosbackend.domain.usuario.endereco.dtos.EnderecoRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.endereco.interfaces.IEnderecoService;
import com.db.crudcursosbackend.domain.usuario.endereco.utils.EnderecoUtils;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IPessoaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/usuario/endereco")
@AllArgsConstructor
public class EnderecoUsuarioController {
    
    private IPessoaService pessoaService;
    private IEnderecoService enderecoService;
    private EnderecoUtils enderecoUtils;

    @PostMapping("/adicionar")
    public ResponseEntity<EnderecoRespostaDTO> adicionarEndereco(
                @RequestBody @Valid EnderecoDTO enderecoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpf = user.getUsername();
        Pessoa pessoa = pessoaService.buscarPorCpf(cpf);
        pessoa.setAtualizadoAs(LocalDateTime.now());
        pessoa.setAtualizadoPor(pessoa.getContato().getEmail());
        Endereco endereco = enderecoDTO.converterParaEntidadeComDono(pessoa);
        Endereco enderecoSalvo = enderecoService.adicionar(endereco);
        EnderecoRespostaDTO resposta = new EnderecoRespostaDTO(enderecoSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<EnderecoRespostaDTO> excluirEndereco(
                @PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String cpf = user.getUsername();
        Pessoa pessoa = enderecoUtils.validarPermissaoDeAlterarEndereco(cpf, id);
        pessoaService.atualizar(pessoa.getCpf(), pessoa, pessoa);
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
        String cpf = user.getUsername();
        Pessoa pessoa = enderecoUtils.validarPermissaoDeAlterarEndereco(cpf, id);
        pessoaService.atualizar(pessoa.getCpf(), pessoa, pessoa);
        Endereco novoEndereco = enderecoDTO.converterParaEntidadeComDono(pessoa);
        Endereco enderecoAtualizado = enderecoService.atualizar(id, novoEndereco);
        EnderecoRespostaDTO resposta = new EnderecoRespostaDTO(enderecoAtualizado);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
