package com.db.crudcursosbackend.domain.usuario.pessoa.servicos;

import com.db.crudcursosbackend.domain.usuario.contato.Contato;
import com.db.crudcursosbackend.domain.usuario.contato.repositorios.ContatoRepository;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.interfaces.IAtualizarPessoaService;
import com.db.crudcursosbackend.domain.usuario.pessoa.repositorio.PessoaRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AtualizarPessoaService implements IAtualizarPessoaService{

    private PessoaRepository pessoaRepository;
    private ContatoRepository contatoRepository;

    @Override
    public Pessoa atualizar(String cpf, Pessoa novaPessoa, Pessoa editor) {
                Pessoa pessoaSalva = pessoaRepository.findByCpf(cpf).orElseThrow();

                pessoaSalva.setCpf(novaPessoa.getCpf());
                pessoaSalva.setNome(novaPessoa.getNome());
                pessoaSalva.setSobrenome(novaPessoa.getSobrenome());
                pessoaSalva.setSenha(novaPessoa.getSenha());
                pessoaSalva.setPapel(novaPessoa.getPapel());
                pessoaSalva.setDataDeNascimento(novaPessoa.getDataDeNascimento());
                pessoaSalva.setAtualizadoAs(LocalDateTime.now());
                pessoaSalva.setAtualizadoPor(editor.getContato().getEmail());

                atualizarContato(pessoaSalva,novaPessoa);
                return pessoaRepository.save(pessoaSalva); 
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
