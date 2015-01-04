package com.dream.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dream.books.domain.Bibliograph;

/**
 * Spring Data JPA repository for the Bibliograph entity.
 */
public interface BibliographRepository extends JpaRepository<Bibliograph, Long> {

    @Query("select bibliograph from Bibliograph bibliograph left join fetch bibliograph.tags where bibliograph.id = :id")
    public Bibliograph findOneWithEagerRelationships(@Param("id") Long id);
    
   
}
