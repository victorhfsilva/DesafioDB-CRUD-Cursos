package com.db.crudcursosbackend.domain.usuario.endereco.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.interfaces.IAdicionarEnderecoService;
import com.db.crudcursosbackend.domain.usuario.endereco.interfaces.IAtualizarEnderecoService;
import com.db.crudcursosbackend.domain.usuario.endereco.interfaces.IBuscarEnderecoPorId;
import com.db.crudcursosbackend.domain.usuario.endereco.interfaces.IEnderecoService;
import com.db.crudcursosbackend.domain.usuario.endereco.interfaces.IExcluirEnderecoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EnderecoService implements IEnderecoService {

    private IAdicionarEnderecoService adicionarEnderecoService;
    private IAtualizarEnderecoService atualizarEnderecoService;
    private IExcluirEnderecoService excluirEnderecoService;
    private IBuscarEnderecoPorId buscarEnderecoPorId;

    @Override
    public Endereco adicionar(Endereco endereco) {
        return adicionarEnderecoService.adicionarEndereco(endereco);
    }

    @Override
    public Endereco atualizar(Long id, Endereco endereco) {
        return atualizarEnderecoService.atualizar(id, endereco);
    }

    @Override
    public Endereco excluir(Long id) {
        return excluirEnderecoService.excluir(id);
    }

    @Override
    public Endereco buscarEnderecoPorId(Long id) {
       return buscarEnderecoPorId.buscarPorId(id);
    }
    
}
