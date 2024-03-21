package com.db.crudcursosbackend.domain.usuario.aluno.servicos;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.repositorios.ContatoRepository;
import com.db.crudcursosbackend.domain.usuario.endereco.Endereco;
import com.db.crudcursosbackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.infra.excecoes.ContatoNulo;
import com.db.crudcursosbackend.infra.validacoes.ValidacaoEditorUtil;
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
        ValidacaoEditorUtil.validarRegistro(aluno, editor);
        
        Contato contato = aluno.getContato();
        salvarContato(contato);

        List<Endereco> enderecos = aluno.getEnderecos();  
        Aluno alunoSalvo = alunoRepository.save(aluno);
    
        salvarEnderecos(enderecos, alunoSalvo);

        return alunoRepository.findById(alunoSalvo.getId()).orElseThrow();
    }

    private void salvarEnderecos(List<Endereco> enderecos, Aluno alunoSalvo) {
        if(enderecos != null) {
            enderecos.stream().forEach(endereco -> {
                endereco.setPessoa(alunoSalvo);
                enderecoRepository.save(endereco);
            });
        }
    }

    private void salvarContato(Contato contato) {
        if (contato != null) {
            contatoRepository.save(contato);
        } else {
            throw new ContatoNulo();
        }
    }
    
}
