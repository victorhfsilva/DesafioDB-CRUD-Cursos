package com.db.crudcursosbackend.domain.usuario.professor.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RespostaRegistrarProfessorDTO {
    
    String token;
    ProfessorRespostaDTO pessoa;
}
