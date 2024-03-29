package com.db.crudcursosbackend.domain.usuario.aluno.servicos;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.repositorios.ContatoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.infra.excecoes.EntidadeDesativada;
import com.db.crudcursosbackend.infra.validacoes.ValidacaoEditorUtil;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.aluno.interfaces.IAtualizarCursoService;
import com.db.crudcursosbackend.domain.usuario.aluno.repositorios.AlunoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AtualizarAlunoService implements IAtualizarCursoService {
    
    private AlunoRepository alunoRepository;
    private ContatoRepository contatoRepository;

    @Override
    public Aluno atualizar(String cpf, Aluno novoAluno, Pessoa editor) {
        
        Aluno alunoSalvo = alunoRepository.findByCpf(cpf).orElseThrow();

        ValidacaoEditorUtil.validarAtualizacao(editor, alunoSalvo);

        String matricula = alunoSalvo.getMatricula();
        LocalDate dataDeIngresso = alunoSalvo.getDataDeIngresso();

        if(alunoSalvo.isAtivo()){
            alunoSalvo.setCpf(novoAluno.getCpf());
            alunoSalvo.setNome(novoAluno.getNome());
            alunoSalvo.setSobrenome(novoAluno.getSobrenome());
            alunoSalvo.setSenha(novoAluno.getSenha());
            alunoSalvo.setPapel(novoAluno.getPapel());
            alunoSalvo.setDataDeNascimento(novoAluno.getDataDeNascimento());
            alunoSalvo.setDataDeIngresso(dataDeIngresso);
            alunoSalvo.setMatricula(matricula);
            atualizarContato(alunoSalvo, novoAluno);
            return alunoRepository.save(alunoSalvo); 
        }
        throw new EntidadeDesativada("O aluno com cpf " + cpf + " foi desativado.");        
    }

    private void atualizarContato(Pessoa pessoaSalva, Pessoa novaPessoa) {
        Contato novoContato = novaPessoa.getContato();
        Contato contatoSalvo = pessoaSalva.getContato();
        if (novoContato != null && !contatoSalvo.equals(novoContato)){
            novoContato.setId(contatoSalvo.getId());
            pessoaSalva.setContato(novaPessoa.getContato());  
            contatoRepository.save(pessoaSalva.getContato());
        }
    }

}
