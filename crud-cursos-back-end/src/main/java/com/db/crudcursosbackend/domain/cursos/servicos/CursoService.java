package com.db.crudcursosbackend.domain.cursos.servicos;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.interfaces.IAdicionarAlunoService;
import com.db.crudcursosbackend.domain.cursos.interfaces.IAtivacaoCursoService;
import com.db.crudcursosbackend.domain.cursos.interfaces.IAtualizarCursoService;
import com.db.crudcursosbackend.domain.cursos.interfaces.IAtualizarProfessorCursoService;
import com.db.crudcursosbackend.domain.cursos.interfaces.ICursoService;
import com.db.crudcursosbackend.domain.cursos.interfaces.IRegistrarCursoService;
import com.db.crudcursosbackend.domain.cursos.interfaces.IRemoverAlunoService;
import com.db.crudcursosbackend.domain.cursos.interfaces.IVerificarAlunoCadastradoService;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CursoService implements ICursoService{
    
    private IRegistrarCursoService registrarCursoService;
    private IAtualizarCursoService atualizarCursoService;
    private IAtualizarProfessorCursoService atualizarProfessorCursoService;
    private ExcluirCursoService excluirCursoService;
    private IAdicionarAlunoService adicionarAlunoService;
    private IRemoverAlunoService removerAlunoService;
    private IVerificarAlunoCadastradoService verificarAlunoCadastradoService;
    private IAtivacaoCursoService ativacaoCursoService;

    @Override
    public Curso registrar(Curso curso, String cpfProfessor, Pessoa editor) {
        return registrarCursoService.registrar(curso, cpfProfessor, editor);
    }

    @Override
    public Curso atualizar(Long cursoId, Curso novoAluno, Pessoa editor) {
        return atualizarCursoService.atualizar(cursoId, novoAluno, editor);
    }

    @Override
    public Curso atualizarProfessor(Long cursoId, String cpfProfessor, Pessoa editor) {
        return atualizarProfessorCursoService.atualizar(cursoId, cpfProfessor, editor);
    }

    @Override
    public Curso excluir(Long cursoId) {
        return excluirCursoService.excluir(cursoId);
    }

    @Override
    public Curso adicionarAluno(Long cursoId, String cpf) {
        return adicionarAlunoService.adicionar(cursoId, cpf);
    }

    @Override
    public Curso removerAluno(Long cursoId, String cpf) {
        return removerAlunoService.remover(cursoId, cpf);
    }

    @Override
    public boolean verificarAlunoCadastrado(String cpfAluno, Long cursoId) {
        return verificarAlunoCadastradoService.verficar(cpfAluno, cursoId);
    }

    @Override
    public Curso ativar(Long cursoId, Pessoa editor) {
        return ativacaoCursoService.ativar(cursoId, editor);
    }

    @Override
    public Curso desativar(Long cursoId, Pessoa editor) {
        return ativacaoCursoService.desativar(cursoId, editor);
    }
    
    

}
