package com.dream.books.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dream.books.domain.ReaderCard;

/**
 * Spring Data JPA repository for the ReaderCard entity.
 */
public interface ReaderCardRepository extends JpaRepository<ReaderCard, Long> {

	 public List<ReaderCard> findByBarcode(final String barcode);
}
