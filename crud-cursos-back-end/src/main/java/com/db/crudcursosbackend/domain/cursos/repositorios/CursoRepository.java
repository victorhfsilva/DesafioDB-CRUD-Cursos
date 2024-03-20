package com.db.crudcursosbackend.domain.cursos.repositorios;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.db.crudcursosbackend.domain.cursos.Curso;

@RepositoryRestResource(path="cursos")
public interface CursoRepository extends JpaRepository<Curso, Long>{
    @RestResource(exported = false)
    @Override
    <S extends Curso> S save(S entity);

    @RestResource(exported = false)
    @Override
    void deleteById(Long id);

    @RestResource(exported = false)
    @Override
    void delete(Curso entity);

    @RestResource(exported = false)
    @Override
    void deleteAll(Iterable<? extends Curso> entities);

    @RestResource(exported = false)
    @Override
    void deleteAll();

    @Query("SELECT c FROM Curso c WHERE c.professor.nome LIKE %:nome%")
    List<Curso> findByProfessorNome(@Param("nome") String nome);
}
