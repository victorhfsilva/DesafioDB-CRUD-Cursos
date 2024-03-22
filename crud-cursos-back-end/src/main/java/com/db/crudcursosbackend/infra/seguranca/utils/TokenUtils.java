package com.db.crudcursosbackend.infra.seguranca.utils;

import org.springframework.stereotype.Component;

import com.db.crudcursosbackend.infra.excecoes.ErroDeAutenticacao;
import com.db.crudcursosbackend.infra.seguranca.servicos.TokenService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TokenUtils {

    private TokenService tokenService;
    
    public String extrairToken(String authorizationHeader) {
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        } else {
            return "";
        }
    }
    
    public String validarToken(String headerAutorizacao){
        String token = extrairToken(headerAutorizacao);
        
        if (!tokenService.isTokenValido(token)){
            throw new ErroDeAutenticacao("Token Inválido.");
        }

        return token;
    }
}
