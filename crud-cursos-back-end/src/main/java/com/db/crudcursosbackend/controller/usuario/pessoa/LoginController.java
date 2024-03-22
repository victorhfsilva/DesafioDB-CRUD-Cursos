package com.db.crudcursosbackend.controller.usuario.pessoa;

import org.springframework.web.bind.annotation.RestController;
import com.db.crudcursosbackend.domain.usuario.pessoa.dtos.LoginDTO;
import com.db.crudcursosbackend.infra.seguranca.interfaces.ITokenService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/login")
@AllArgsConstructor
public class LoginController {
    
    private AuthenticationManager authenticationManager;
    private ITokenService tokenService;

    @PostMapping("")
    @Operation(summary = "Faz login na api e obtém um Token JWT.")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                new UsernamePasswordAuthenticationToken(login.getCpf(), login.getSenha());
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = tokenService.gerarToken(login.getCpf());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
    
}
