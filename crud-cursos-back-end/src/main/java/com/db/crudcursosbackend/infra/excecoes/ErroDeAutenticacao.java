package com.db.crudcursosbackend.infra.excecoes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErroDeAutenticacao extends RuntimeException {
    private String mensagem;
}
