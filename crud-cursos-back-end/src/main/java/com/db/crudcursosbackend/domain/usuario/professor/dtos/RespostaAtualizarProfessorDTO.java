package com.db.crudcursosbackend.domain.usuario.professor.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.contato.dtos.ContatoRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;

@Getter
@AllArgsConstructor
public class RespostaAtualizarProfessorDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private LocalDate dataDeNascimento;
    private ContatoRespostaDTO contato;
    private double salario;

    public RespostaAtualizarProfessorDTO(Professor professor){
        this.id = professor.getId();
        this.nome = professor.getNome();
        this.sobrenome = professor.getSobrenome();
        this.cpf = professor.getCpf();
        this.dataDeNascimento = professor.getDataDeNascimento();
        this.contato = new ContatoRespostaDTO(professor.getContato());
    }
}
