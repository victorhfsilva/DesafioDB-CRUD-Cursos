package com.db.crudcursosbackend.domain.usuario.aluno.dtos;

import java.time.LocalDate;
import java.util.List;

import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.contato.dtos.ContatoRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.endereco.dtos.EnderecoRespostaDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlunoRespostaDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private LocalDate dataDeNascimento;
    private ContatoRespostaDTO contato;
    private List<EnderecoRespostaDTO> enderecos;
    private String matricula;
    private LocalDate dataDeIngresso;
    private List<Curso> cursos;

    public AlunoRespostaDTO(Aluno aluno){
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.sobrenome = aluno.getSobrenome();
        this.cpf = aluno.getCpf();
        this.dataDeNascimento = aluno.getDataDeNascimento();
        this.contato = new ContatoRespostaDTO(aluno.getContato());
        this.enderecos = aluno.getEnderecos().stream()
                                                .map(endereco -> new EnderecoRespostaDTO(endereco))
                                                .toList();  
        this.matricula = aluno.getMatricula();
        this.dataDeIngresso = aluno.getDataDeIngresso();
        this.cursos = aluno.getCursos();
    }
}
