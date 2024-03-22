package com.db.crudcursosbackend.infra.seguranca.servicos;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.domain.usuario.pessoa.repositorio.PessoaRepository;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PessoaUserDetailsService implements UserDetailsService {

    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Pessoa pessoa = pessoaRepository.findByCpf(cpf).orElseThrow();
        String senha = pessoa.getSenha();
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(pessoa.getPapel().name()));
        return new User(cpf, senha, authorities);
    }
    
}
