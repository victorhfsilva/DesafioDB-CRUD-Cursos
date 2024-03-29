package com.db.crudcursosbackend.domain.usuario.professor;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "professores")
public class Professor extends Pessoa {
    
    @Column(name = "salario", columnDefinition = "NUMERIC(9,2)", nullable = false)
    private double salario;

    @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Curso> cursos;

        public Professor(
            boolean active,
            String createdBy, 
            String updatedBy, 
            String deactivatedBy, 
            LocalDateTime createdAt, 
            LocalDateTime updatedAt, 
            LocalDateTime deactivatedAt,
            String nome, 
            String sobrenome, 
            String cpf, 
            String senha, 
            Papel papel, 
            LocalDate dataDeNascimento,
            Contato contato,
            List<Endereco> enderecos,
            double salario,
            List<Curso> cursos) {      
        
        super(active, 
                createdBy, 
                updatedBy, 
                deactivatedBy, 
                createdAt, 
                updatedAt, 
                deactivatedAt,
                nome,
                sobrenome,
                cpf,
                senha,
                papel,
                dataDeNascimento,
                contato,
                enderecos);

        this.salario = salario;
        this.cursos = cursos;
    }



}
