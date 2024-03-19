package com.db.crudcursosbackend.domain.usuario.endereco.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.interfaces.IBuscarEnderecoPorId;
import com.db.crudcursosbackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BuscarEnderecoPorId implements IBuscarEnderecoPorId {

    private EnderecoRepository enderecoRepository;
    
    @Override
    public Endereco buscarPorId(Long id) {
       return enderecoRepository.findById(id).orElseThrow();
    }
    
}
