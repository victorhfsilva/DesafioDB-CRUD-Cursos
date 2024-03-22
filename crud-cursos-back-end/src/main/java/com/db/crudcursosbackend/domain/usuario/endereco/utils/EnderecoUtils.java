package com.db.crudcursosbackend.domain.usuario.endereco.utils;

import org.springframework.stereotype.Component;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.interfaces.IEnderecoService;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudcursosbackend.infra.excecoes.ErroDeAutenticacao;
import com.db.crudcursosbackend.infra.seguranca.interfaces.ITokenService;
import com.db.crudcursosbackend.infra.seguranca.utils.TokenUtils;
import java.util.List;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EnderecoUtils {
    
    private TokenUtils tokenUtils;
    private ITokenService tokenService;
    private IPessoaService pessoaService;
    private IEnderecoService enderecoService;

    public Pessoa validarPermissaoDeAlterarEndereco(String headerAutorizacao, long id) {
        String token = tokenUtils.validarToken(headerAutorizacao);
        String cpf = tokenService.obterSujeito(token);
        Pessoa pessoa = pessoaService.buscarPorCpf(cpf);
        Endereco endereco = enderecoService.buscarEnderecoPorId(id);
        List<Endereco> enderecos = pessoa.getEnderecos();
        if (!enderecos.contains(endereco)){
            throw new ErroDeAutenticacao("Este endereço não pertence a este usuário.");
        }
        return pessoa;
    }
}
