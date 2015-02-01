package com.dream.books.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dream.books.domain.BorrowHistory;

/**
 * Spring Data JPA repository for the BorrowHistory entity.
 */
public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Long> {

	@Query("select history from BorrowHistory history where history.item.barcode = :barcode")
	public BorrowHistory getByItemBarcode(@Param("barcode")  String barcode);

	@Query("select history from BorrowHistory history where history.item.bibliograph.callNumber like :callNumber")
  	public List<BorrowHistory> getByCallNumberLike(@Param("callNumber")  String callNumber);

   @Query("select history from BorrowHistory history where history.item.bibliograph.title like :title")
  	public List<BorrowHistory> getByTitleLike(@Param("title")  String title);
}
