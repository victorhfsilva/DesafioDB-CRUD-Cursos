package com.db.crudcursosbackend.domain.usuario.aluno.servicos;

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
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IRegistrarAlunoService;
import com.db.crudcursosbackend.domain.usuario.aluno.repositorios.AlunoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrarAlunoService implements IRegistrarAlunoService {

    private EnderecoRepository enderecoRepository;
    private ContatoRepository contatoRepository;
    private AlunoRepository alunoRepository;

    @Override
    public Aluno registrar(Aluno aluno, Pessoa editor) {
        if (editor != null){
            aluno.setAtivo(true);
            aluno.setCriadoAs(LocalDateTime.now());
            aluno.setCriadoPor(editor.getContato().getEmail());
        }

        Contato contato = aluno.getContato();
        contatoRepository.save(contato);

        List<Endereco> enderecos = aluno.getEnderecos();
        List<Curso> cursos = aluno.getCursos();    

        Aluno alunoSalvo = alunoRepository.save(aluno);
    
        if(enderecos != null) {
            enderecos.stream().forEach(endereco -> {
                endereco.setPessoa(alunoSalvo);
                enderecoRepository.save(endereco);
            });
        }

        return alunoRepository.findById(alunoSalvo.getId()).orElseThrow();
    }
    
}