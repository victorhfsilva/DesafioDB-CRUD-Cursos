package com.db.crudcursosbackend.domain.usuario.professor.dtos;

import java.time.LocalDate;
import java.util.List;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.usuario.contato.dtos.ContatoRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.endereco.dtos.EnderecoRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfessorRespostaDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private LocalDate dataDeNascimento;
    private ContatoRespostaDTO contato;
    private List<EnderecoRespostaDTO> enderecos;
    private double salario;
    private List<Curso> cursos;

    public ProfessorRespostaDTO(Professor professor){
        this.id = professor.getId();
        this.nome = professor.getNome();
        this.sobrenome = professor.getSobrenome();
        this.cpf = professor.getCpf();
        this.dataDeNascimento = professor.getDataDeNascimento();
        this.contato = new ContatoRespostaDTO(professor.getContato());
        this.enderecos = professor.getEnderecos().stream()
                                                .map(endereco -> new EnderecoRespostaDTO(endereco))
                                                .toList();  
        this.salario = professor.getSalario();
        this.cursos = professor.getCursos();
    }
}
