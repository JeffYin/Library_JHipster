package com.dream.books.repository;

import com.dream.books.domain.BorrowHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the BorrowHistory entity.
 */
public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Long> {

}
