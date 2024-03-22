package com.db.crudcursosbackend.infra.seguranca.servicos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.db.crudcursosbackend.domain.usuario.pessoa.repositorio.PessoaRepository;
import com.db.crudcursosbackend.infra.seguranca.interfaces.ITokenService;

@Service
public class TokenService implements ITokenService {

    private String jwtSecret = "secret";
    private String issuer = "DB";
    private Algorithm algoritmo = Algorithm.HMAC256(jwtSecret);
    private PessoaRepository pessoaRepository;

    public TokenService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public String gerarToken(String cpf){

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(cpf)
                .withExpiresAt(LocalDateTime.now().plusHours(1L).toInstant(ZoneOffset.of("-03:00")))
                .sign(algoritmo);
    }

    public boolean isTokenValido(String token){
        try {
            JWT.require(algoritmo)
                .withIssuer(issuer)
                .build()
                .verify(token);
            String cpf = JWT.decode(token).getSubject();
            return pessoaRepository.findByCpf(cpf).isPresent();
        } catch (Exception ex) {
            return false;
        }
    }

    public String obterSujeito(String token) {
        return JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getSubject();
    }
}
