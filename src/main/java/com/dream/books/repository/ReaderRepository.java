package com.dream.books.repository;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.dream.books.domain.Reader;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Reader entity.
 */
public interface ReaderRepository extends JpaRepository<Reader, Long> {
	//The implementation is implemented in Reader.java
	public List<Reader> getByNameLike(final String partialName);
}
