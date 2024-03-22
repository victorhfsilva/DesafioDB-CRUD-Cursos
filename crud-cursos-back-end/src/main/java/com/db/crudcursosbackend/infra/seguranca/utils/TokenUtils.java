package com.db.crudcursosbackend.infra.seguranca.utils;

import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TokenUtils {
    
    public String extrairToken(String authorizationHeader) {
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        } else {
            return "";
        }
    }
    
}
