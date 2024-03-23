package com.db.crudcursosbackend.domain.usuario.professor.dtos;

import java.time.LocalDate;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.dtos.ContatoDTO;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.ProfessorBuilder;
import com.db.crudcursosbackend.infra.validacoes.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class AtualizarProfessorDTO {
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

    @NotNull
    private double salario;

    public Professor converterParaEntidade(PasswordEncoder passwordEncoder, Papel papel){
        ProfessorBuilder professorBuilder = new ProfessorBuilder();
        
        Contato contatoEntidade = contato.converterParaEntidade();
       
        return professorBuilder.nome(nome)
                            .sobrenome(sobrenome)
                            .cpf(cpf)
                            .senha(passwordEncoder.encode(senha))
                            .papel(papel)
                            .dataDeNascimento(dataDeNascimento)
                            .contato(contatoEntidade)
                            .salario(salario)
                            .build();
    }
}
