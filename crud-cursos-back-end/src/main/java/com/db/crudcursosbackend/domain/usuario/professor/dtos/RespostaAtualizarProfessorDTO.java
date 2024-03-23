package com.db.crudcursosbackend.domain.usuario.professor.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.contato.dtos.ContatoRespostaDTO;

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

    public RespostaAtualizarProfessorDTO(Aluno aluno){
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.sobrenome = aluno.getSobrenome();
        this.cpf = aluno.getCpf();
        this.dataDeNascimento = aluno.getDataDeNascimento();
        this.contato = new ContatoRespostaDTO(aluno.getContato());
    }
}
