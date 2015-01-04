package com.dream.books.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dream.books.domain.Item;

/**
 * Spring Data JPA repository for the Item entity.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
  
	/* Find the item by the given barcode */
	public List<Item> findByBarcode(String barcode);
	
	@Query("select item from Item item where item.bibliograph.callNumber like :callNumber")
  	public List<Item> getByCallNumberLike(@Param("callNumber")  String callNumber);

   @Query("select item from Item item where item.bibliograph.title like :title")
  	public List<Item> getByTitleLike(@Param("title")  String title);
}
