package com.db.crudcursosbackend.domain.usuario.aluno.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import com.db.crudcursosbackend.domain.usuario.aluno.Aluno;
import com.db.crudcursosbackend.domain.usuario.estado.Estado;

@RepositoryRestResource(path="alunos")
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    
    @RestResource(exported = false)
    @Override
    <S extends Aluno> S save(S entity);

    @RestResource(exported = false)
    @Override
    void deleteById(Long id);

    @RestResource(exported = false)
    @Override
    void delete(Aluno entity);

    @RestResource(exported = false)
    @Override
    void deleteAll(Iterable<? extends Aluno> entities);

    @RestResource(exported = false)
    @Override
    void deleteAll();
    
    Optional<Aluno> findByCpf(String cpf);

    Optional<Aluno> findByMatricula(String matricula);

    @Query("SELECT a FROM Aluno a WHERE a.contato.email = :email")
    Optional<Aluno> findByEmail(String email);

    @Query("SELECT a FROM Aluno a WHERE a.contato.celular = :celular")
    Optional<Aluno> findByCelular(String celular);

    @Query("SELECT a FROM Aluno a JOIN a.enderecos e WHERE e.cidade LIKE %:cidade%")
    List<Aluno> findByCidade(@Param("cidade") String cidade);

    @Query("SELECT a FROM Aluno a JOIN a.enderecos e WHERE e.rua LIKE %:rua%")
    List<Aluno> findByRua(@Param("rua") String rua);

    @Query("SELECT a FROM Aluno a JOIN a.enderecos e WHERE e.bairro LIKE %:bairro%")
    List<Aluno> findByBairro(@Param("bairro") String bairro);

    @Query("SELECT a FROM Aluno a JOIN a.enderecos e WHERE e.cep = :cep")
    List<Aluno> findByCep(@Param("cep") String cep);

    @Query("SELECT a FROM Aluno a JOIN a.enderecos e WHERE e.estado = :estado")
    List<Aluno> findByEstado(@Param("estado") Estado estado);

    @Query("SELECT a FROM Aluno a JOIN a.cursos c WHERE c.nome LIKE %:nome%")
    List<Aluno> findByCursoNome(@Param("nome") String nome);

    @Query("SELECT a FROM Aluno a JOIN a.cursos c WHERE c.id = :id")
    List<Aluno> findByCursoId(@Param("id") Long id);
}
