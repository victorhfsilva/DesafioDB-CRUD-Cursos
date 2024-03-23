package com.db.crudcursosbackend.domain.usuario.aluno.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RespostaRegistrarAlunoDTO {
    
    String token;
    AlunoRespostaDTO pessoa;
}
