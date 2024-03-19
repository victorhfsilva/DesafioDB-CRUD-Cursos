package com.db.crudcursosbackend.domain.usuario.professor.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import com.db.crudcursosbackend.domain.usuario.professor.Professor;
import com.db.crudcursosbackend.domain.usuario.estado.Estado;

@RepositoryRestResource(path="professores")
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    
    @RestResource(exported = false)
    @Override
    <S extends Professor> S save(S entity);

    @RestResource(exported = false)
    @Override
    void deleteById(Long id);

    @RestResource(exported = false)
    @Override
    void delete(Professor entity);

    @RestResource(exported = false)
    @Override
    void deleteAll(Iterable<? extends Professor> entities);

    @RestResource(exported = false)
    @Override
    void deleteAll();
    
    Optional<Professor> findByCpf(String cpf);

    @Query("SELECT p FROM Professor p WHERE p.contato.email = :email")
    Optional<Professor> findByEmail(String email);

    @Query("SELECT p FROM Professor p WHERE p.contato.celular = :celular")
    Optional<Professor> findByCelular(String celular);

    @Query("SELECT p FROM Professor p JOIN p.enderecos e WHERE e.cidade = :cidade")
    List<Professor> findByCidade(@Param("cidade") String cidade);

    @Query("SELECT p FROM Professor p JOIN p.enderecos e WHERE e.rua = :rua")
    List<Professor> findByRua(@Param("rua") String rua);

    @Query("SELECT p FROM Professor p JOIN p.enderecos e WHERE e.bairro = :bairro")
    List<Professor> findByBairro(@Param("bairro") String bairro);

    @Query("SELECT p FROM Professor p JOIN p.enderecos e WHERE e.cep = :cep")
    List<Professor> findByCep(@Param("cep") String cep);

    @Query("SELECT p FROM Professor p JOIN p.enderecos e WHERE e.estado = :estado")
    List<Professor> findByEstado(@Param("estado") Estado estado);
}
