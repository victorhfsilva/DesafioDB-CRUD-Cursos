package com.db.crudcursosbackend.domain.cursos.dtos;

import com.db.crudcursosbackend.domain.cursos.Curso;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RespostaAtualizarCursoDTO {
    private String nome;
    private String descricao;
    private int cargaHoraria;

    public RespostaAtualizarCursoDTO(Curso curso){
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
        this.cargaHoraria = curso.getCargaHoraria();     
    }
}
