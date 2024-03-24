package com.db.crudcursosbackend.domain.cursos.dtos;

import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.CursoBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CursoDTO {
    
    @NotBlank
    private String nome;

    @NotBlank
    private String descricao; 

    @NotNull
    private int cargaHoraria;

    public Curso converterParaEntidade(){
        CursoBuilder cursoBuilder = new CursoBuilder();
        return cursoBuilder.nome(nome)
                    .descricao(descricao)
                    .cargaHoraria(cargaHoraria)
                    .build();
    }

}
