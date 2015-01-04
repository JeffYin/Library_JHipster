package com.dream.books.repository;

import com.dream.books.domain.Bibliograph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Bibliograph entity.
 */
public interface BibliographRepository extends JpaRepository<Bibliograph, Long> {

    @Query("select bibliograph from Bibliograph bibliograph left join fetch bibliograph.tags where bibliograph.id = :id")
    Bibliograph findOneWithEagerRelationships(@Param("id") Long id);

}
