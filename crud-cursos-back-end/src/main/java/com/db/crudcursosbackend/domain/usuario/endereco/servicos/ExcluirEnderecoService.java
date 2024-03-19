package com.db.crudcursosbackend.domain.usuario.endereco.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.interfaces.IExcluirEnderecoService;
import com.db.crudcursosbackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExcluirEnderecoService implements IExcluirEnderecoService{

    private EnderecoRepository enderecoRepository;
    private BuscarEnderecoPorId buscarEnderecoPorId;

    @Override
    public Endereco excluir(Long id) {
        Endereco endereco = buscarEnderecoPorId.buscarPorId(id);
        enderecoRepository.delete(endereco);
        return endereco;
    }
    
}
