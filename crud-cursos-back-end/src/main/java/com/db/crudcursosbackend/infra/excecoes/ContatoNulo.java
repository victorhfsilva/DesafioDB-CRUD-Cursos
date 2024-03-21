package com.db.crudcursosbackend.infra.excecoes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ContatoNulo extends RuntimeException {
    private String mensagem;
}
