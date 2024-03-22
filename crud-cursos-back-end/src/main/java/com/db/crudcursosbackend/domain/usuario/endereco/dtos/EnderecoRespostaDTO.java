package com.db.crudcursosbackend.domain.usuario.endereco.dtos;

import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.estado.Estado;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnderecoRespostaDTO {
    private Long id;
    private String numero;
    private String complemento;
    private String rua;
    private String bairro;
    private String cidade;
    private Estado estado;
    private String cep;

    public EnderecoRespostaDTO(Endereco endereco){
        this.id = endereco.getId();
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.rua = endereco.getRua();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        this.cep = endereco.getCep();
    }
}
