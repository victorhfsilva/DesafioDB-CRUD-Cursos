package com.db.crudcursosbackend.domain.usuario.aluno.servicos;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.repositorios.ContatoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IAtualizarAlunoService;
import com.db.crudcursosbackend.domain.usuario.aluno.repositorios.AlunoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AtualizarAlunoService implements IAtualizarAlunoService {
    
    private AlunoRepository alunoRepository;
    private ContatoRepository contatoRepository;

    @Override
    public Aluno atualizar(String cpf, Aluno novoAluno, Pessoa editor) {
        
        Aluno alunoSalvo = alunoRepository.findByCpf(cpf).orElseThrow();

        alunoSalvo.setCpf(novoAluno.getCpf());
        alunoSalvo.setNome(novoAluno.getNome());
        alunoSalvo.setSobrenome(novoAluno.getSobrenome());
        alunoSalvo.setSenha(novoAluno.getSenha());
        alunoSalvo.setPapel(novoAluno.getPapel());
        alunoSalvo.setDataDeNascimento(novoAluno.getDataDeNascimento());
        alunoSalvo.setMatricula(novoAluno.getMatricula());
        alunoSalvo.setDataDeIngresso(novoAluno.getDataDeIngresso());

        alunoSalvo.setAtualizadoAs(LocalDateTime.now());
        alunoSalvo.setAtualizadoPor(editor.getContato().getEmail());

        atualizarContato(alunoSalvo,novoAluno);
        return alunoRepository.save(alunoSalvo); 

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
