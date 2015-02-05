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

	@Query("select history from BorrowHistory history where history.item.barcode = :barcode and history.cleared is false")
	public BorrowHistory getBorrowedItemByBarcode(@Param("barcode")  String barcode);

	@Query("select history from BorrowHistory history where history.item.bibliograph.callNumber like :callNumber and history.cleared is false")
  	public List<BorrowHistory> getBorrowedItemByCallNumberLike(@Param("callNumber")  String callNumber);

   @Query("select history from BorrowHistory history where history.item.bibliograph.title like :title and history.cleared is false")
  	public List<BorrowHistory> getBorrowedItemByTitleLike(@Param("title")  String title);
}
