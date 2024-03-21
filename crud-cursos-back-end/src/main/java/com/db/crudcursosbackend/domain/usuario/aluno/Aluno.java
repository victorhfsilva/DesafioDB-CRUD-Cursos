package com.db.crudcursosbackend.domain.usuario.aluno;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.papel.Papel;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@ToString()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "alunos")
public class Aluno extends Pessoa {
    
    @Column(name = "matricula", nullable = false, unique = true, columnDefinition = "VARCHAR(10)")
    private String matricula;

    @Column(name = "data_ingresso", nullable = false)
    private LocalDate dataDeIngresso;

    @ManyToMany(mappedBy = "alunos", cascade = CascadeType.REMOVE)
    private List<Curso> cursos;

    public Aluno(
            boolean active,
            String criadoPor, 
            String atualizadoPor, 
            String desativadoPor, 
            LocalDateTime criadoAs, 
            LocalDateTime atualizadoAs, 
            LocalDateTime desativadoAs,
            String nome, 
            String sobrenome, 
            String cpf, 
            String senha, 
            Papel papel, 
            LocalDate dataDeNascimento,
            Contato contato,
            List<Endereco> enderecos,
            String matricula,
            LocalDate dataDeIngresso,
            List<Curso> cursos) {      
        
        super(active, 
                criadoPor, 
                atualizadoPor, 
                desativadoPor, 
                criadoAs, 
                atualizadoAs, 
                desativadoAs,
                nome,
                sobrenome,
                cpf,
                senha,
                papel,
                dataDeNascimento,
                contato,
                enderecos);

        this.matricula = matricula;
        this.dataDeIngresso = dataDeIngresso;
        this.cursos = cursos;
    }
}
