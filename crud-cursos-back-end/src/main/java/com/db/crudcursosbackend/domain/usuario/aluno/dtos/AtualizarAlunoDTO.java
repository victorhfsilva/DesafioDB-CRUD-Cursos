package com.db.crudcursosbackend.domain.usuario.aluno.dtos;

import java.time.LocalDate;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.AlunoBuilder;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.dtos.ContatoDTO;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.infra.validacoes.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class AtualizarAlunoDTO {
    @NotBlank
    private String nome;
    
    @NotBlank
    private String sobrenome;
    
    @CPF
    private String cpf;
    
    @ValidPassword
    private String senha;

    @NotNull
    private LocalDate dataDeNascimento;

    @NotNull
    private ContatoDTO contato;

    public Aluno converterParaEntidade(PasswordEncoder passwordEncoder, Papel papel){
        AlunoBuilder alunoBuilder = new AlunoBuilder();
        
        Contato contatoEntidade = contato.converterParaEntidade();
       
        return alunoBuilder.nome(nome)
                            .sobrenome(sobrenome)
                            .cpf(cpf)
                            .senha(passwordEncoder.encode(senha))
                            .papel(papel)
                            .dataDeNascimento(dataDeNascimento)
                            .contato(contatoEntidade)
                            .build();
    }
}
