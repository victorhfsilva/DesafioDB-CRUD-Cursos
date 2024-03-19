package com.db.crudcursosbackend.domain.usuario.endereco.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.interfaces.IAdicionarEnderecoService;
import com.db.crudcursosbackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdicionarEnderecoService implements IAdicionarEnderecoService {

    private EnderecoRepository enderecoRepository;

    @Override
    public Endereco adicionarEndereco(Endereco endereco) {
       return enderecoRepository.save(endereco);
    }
    
}
