package com.db.crudcursosbackend.domain.usuario.pessoa;

import java.time.LocalDate;
import java.util.List;
import com.db.crudcursosbackend.domain.base.EntidadeBaseAudicao;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@ToString()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public class Pessoa extends EntidadeBaseAudicao {

    @Column(name = "nome", nullable = false, columnDefinition = "VARCHAR(50)")
    private String nome;
    
    @Column(name = "sobrenome", nullable = false, columnDefinition = "VARCHAR(50)")
    private String sobrenome;
    
    @Column(name = "cpf", unique = true, nullable = false, columnDefinition = "VARCHAR(50)")
    private String cpf;
    
    @Column(name = "senha", nullable = false, columnDefinition = "VARCHAR(1023)")
    @JsonIgnore
    private String senha;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "papel", nullable = false)
    private Papel papel;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "contato_id")
    private Contato contato;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Endereco> enderecos;

    public Pessoa(
            boolean ativo,
            String criadoPor, 
            String atualizadoPor, 
            String desativadoPor, 
            LocalDateTime criadaAs, 
            LocalDateTime atualizadaAs, 
            LocalDateTime desativadoAs,
            String nome, 
            String sobrenome, 
            String cpf, 
            String senha, 
            Papel papel, 
            LocalDate dataDeNascimento,
            Contato contato,
            List<Endereco> enderecos) {      
        super(ativo, criadoPor, atualizadoPor, desativadoPor, criadaAs, atualizadaAs, desativadoAs);
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.senha = senha;
        this.papel = papel;
        this.dataDeNascimento = dataDeNascimento;
        this.contato = contato;
        this.enderecos = enderecos;
    }
}
