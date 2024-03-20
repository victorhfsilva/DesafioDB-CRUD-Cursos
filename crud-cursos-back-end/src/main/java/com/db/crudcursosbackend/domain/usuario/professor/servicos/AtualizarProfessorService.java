package com.db.crudcursosbackend.domain.usuario.professor.servicos;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.repositorios.ContatoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.professor.interfaces.IAtualizarProfessorService;
import com.db.crudcursosbackend.domain.usuario.professor.repositorios.ProfessorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AtualizarProfessorService implements IAtualizarProfessorService {
    
    private ProfessorRepository professorRepository;
    private ContatoRepository contatoRepository;

    @Override
    public Professor atualizar(String cpf, Professor novoProfessor, Pessoa editor) {
        
        Professor professorSalvo = professorRepository.findByCpf(cpf).orElseThrow();

        professorSalvo.setCpf(novoProfessor.getCpf());
        professorSalvo.setNome(novoProfessor.getNome());
        professorSalvo.setSobrenome(novoProfessor.getSobrenome());
        professorSalvo.setSenha(novoProfessor.getSenha());
        professorSalvo.setPapel(novoProfessor.getPapel());
        professorSalvo.setDataDeNascimento(novoProfessor.getDataDeNascimento());
        professorSalvo.setSalario(novoProfessor.getSalario());
        
        if (editor != null){
            professorSalvo.setAtualizadoAs(LocalDateTime.now());
            professorSalvo.setAtualizadoPor(editor.getContato().getEmail());
        }

        atualizarContato(professorSalvo,novoProfessor);
        return professorRepository.save(professorSalvo); 
    }

    private void atualizarContato(Pessoa pessoaSalva, Pessoa novaPessoa) {
        Contato novoContato = novaPessoa.getContato();
        Contato contatoSalvo = pessoaSalva.getContato();
        if (!contatoSalvo.equals(novoContato)){
            novoContato.setId(contatoSalvo.getId());
            pessoaSalva.setContato(novaPessoa.getContato());  
            contatoRepository.save(pessoaSalva.getContato());
        }
    }

}
