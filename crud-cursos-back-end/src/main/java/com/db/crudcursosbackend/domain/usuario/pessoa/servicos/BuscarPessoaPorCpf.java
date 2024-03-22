package com.db.crudcursosbackend.domain.usuario.pessoa.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IBuscarPessoaPorCpf;
import com.db.crudcursosbackend.domain.usuario.pessoa.repositorio.PessoaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BuscarPessoaPorCpf implements IBuscarPessoaPorCpf {

    private PessoaRepository pessoaRepository;

    @Override
    public Pessoa buscarPorCpf(String cpf) {
        return pessoaRepository.findByCpf(cpf).orElseThrow();
    }
    
}
