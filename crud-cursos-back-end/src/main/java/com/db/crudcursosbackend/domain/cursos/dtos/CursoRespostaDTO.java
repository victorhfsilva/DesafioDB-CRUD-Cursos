package com.db.crudcursosbackend.domain.cursos.dtos;

import java.util.List;

import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.usuario.aluno.dtos.AlunoRespostaDTO;
import com.db.crudcursosbackend.domain.usuario.professor.dtos.ProfessorRespostaDTO;

public class CursoRespostaDTO {
    private String nome;
    private String descricao;
    private int cargaHoraria;
    private ProfessorRespostaDTO professor;
    private List<AlunoRespostaDTO> alunos;

    public CursoRespostaDTO(Curso curso){
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
        this.cargaHoraria = curso.getCargaHoraria();
        
        if (curso.getProfessor() != null){
            this.professor = new ProfessorRespostaDTO(curso.getProfessor());
        }

        if (curso.getAlunos() != null){
            this.alunos = curso.getAlunos().stream()
                                            .map(aluno -> new AlunoRespostaDTO(aluno))
                                            .toList();
        }
        
    }
}
