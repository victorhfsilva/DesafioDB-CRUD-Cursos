package com.db.crudcursosbackend.domain.cursos.servicos;

import org.springframework.stereotype.Service;

import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.interfaces.IAdicionarAlunoService;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.repositorios.AlunoRepository;
import com.db.crudcursosbackend.infra.excecoes.EntidadeDesativada;

import java.util.List;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdicionarAlunoService implements IAdicionarAlunoService {
    
    private CursoRepository cursoRepository;
    private AlunoRepository alunoRepository;

    @Override
    public Curso adicionar(Long cursoId, String cpf) {
        Aluno aluno = alunoRepository.findByCpf(cpf).orElseThrow();
        Curso curso = cursoRepository.findById(cursoId).orElseThrow();

        if (aluno.isAtivo()){
            if (curso.isAtivo()){
                List<Aluno> alunos = curso.getAlunos();
                alunos.add(aluno);
                curso.setAlunos(alunos);
            } else {
                throw new EntidadeDesativada("Este curso foi desativado.");
            }
            throw new EntidadeDesativada("O aluno com cpf " + cpf + " foi desativada.");  
        } 

        return cursoRepository.save(curso);
    }
}
