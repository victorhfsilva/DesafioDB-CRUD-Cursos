package com.db.crudcursosbackend.domain.usuario.pessoa.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;

@RepositoryRestResource(path="pessoas")
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @RestResource(exported = false)
    @Override
    <S extends Pessoa> S save(S entity);

    @RestResource(exported = false)
    @Override
    void deleteById(Long id);

    @RestResource(exported = false)
    @Override
    void delete(Pessoa entity);

    @RestResource(exported = false)
    @Override
    void deleteAll(Iterable<? extends Pessoa> entities);

    @RestResource(exported = false)
    @Override
    void deleteAll();

    Optional<Pessoa> findByCpf(String cpf);
} 