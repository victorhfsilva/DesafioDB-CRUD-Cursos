package com.db.crudcursosbackend.domain.usuario.pessoa.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IAtualizarPessoaService;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IBuscarPessoaPorCpf;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IPessoaService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PessoaService implements IPessoaService {

    IBuscarPessoaPorCpf buscarPessoaPorCpf;
    IAtualizarPessoaService atualizarPessoaService;

    @Override
    public Pessoa atualizar(String cpf, Pessoa novaPessoa, Pessoa editor) {
        return atualizarPessoaService.atualizar(cpf, novaPessoa, editor);
    }

    @Override
    public Pessoa buscarPorCpf(String cpf) {
        return buscarPessoaPorCpf.buscarPorCpf(cpf);
    }
    
}
