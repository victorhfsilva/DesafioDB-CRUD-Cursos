package com.db.crudcursosbackend.domain.usuario.professor.servicos;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.cursos.repositorios.CursoRepository;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.repositorios.ContatoRepository;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IRegistrarProfessorService;
import com.db.crudcursosbackend.domain.usuario.professor.repositorios.ProfessorRepository;
import com.db.crudcursosbackend.infra.validacoes.ValidacaoEditorUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrarProfessorService implements IRegistrarProfessorService {

    private EnderecoRepository enderecoRepository;
    private ContatoRepository contatoRepository;
    private ProfessorRepository professorRepository;
    private CursoRepository cursoRepository;

    @Override
    public Professor registrar(Professor professor, Pessoa editor) {
        ValidacaoEditorUtil.validarRegistro(professor, editor);

        Contato contato = professor.getContato();
        contatoRepository.save(contato);

        List<Endereco> enderecos = professor.getEnderecos();
        List<Curso> cursos = professor.getCursos();    

        Professor professorSalvo = professorRepository.save(professor);
    
        if(enderecos != null) {
            enderecos.stream().forEach(endereco -> {
                endereco.setPessoa(professorSalvo);
                enderecoRepository.save(endereco);
            });
        }

        if(cursos != null){
            cursos.stream().forEach(curso -> {
                curso.setProfessor(professor);
                cursoRepository.save(curso);
            });
        }

        return professorRepository.findById(professorSalvo.getId()).orElseThrow();
    }
    
}
