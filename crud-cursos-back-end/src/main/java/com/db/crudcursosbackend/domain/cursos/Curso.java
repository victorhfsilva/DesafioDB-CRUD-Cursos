package com.db.crudcursosbackend.domain.cursos;

import java.time.LocalDateTime;
import java.util.List;
import com.db.crudcursosbackend.domain.base.EntidadeBaseAudicao;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@ToString()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cursos")
public class Curso extends EntidadeBaseAudicao {
    
    @Column(name = "nome", nullable = false, columnDefinition = "VARCHAR(50)")
    private String nome;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao; 

    @Column(name = "carga_horaria", nullable = false, columnDefinition = "SMALLINT")
    private int cargaHoraria;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToMany(mappedBy = "cursos")
    List<Aluno> alunos;

        public Curso(
            boolean ativo,
            String criadoPor, 
            String atualizadoPor, 
            String desativadoPor, 
            LocalDateTime criadoAs, 
            LocalDateTime atualizadoAs, 
            LocalDateTime desativadoAs,
            String nome,
            String descricao,
            int cargaHoraria,
            Professor professor,
            List<Aluno> alunos ) {      
        super(ativo, criadoPor, atualizadoPor, desativadoPor, criadoAs, atualizadoAs, desativadoAs);
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.professor = professor;
        this.alunos = alunos;
    }
}
