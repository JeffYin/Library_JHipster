package com.dream.books.web.rest;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.dream.books.data.ItemStatus;
import com.dream.books.domain.BorrowHistory;
import com.dream.books.domain.Item;
import com.dream.books.repository.BorrowHistoryRepository;
import com.dream.books.repository.ItemRepository;

/**
 * REST controller for managing BorrowHistory.
 */
@RestController
@RequestMapping("/app")
public class CheckinResource {
    private final Logger log = LoggerFactory.getLogger(CheckinResource.class);

    @Inject
    private BorrowHistoryRepository borrowHistoryRepository;
    
    @Inject
    private ItemRepository itemRepository;
    
    
    
    /**
     * POST  /rest/checkin -> Create a new borrowHistory.
     */
    @RequestMapping(value = "/rest/checkin/barcode",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void checkInByItemBarcode(@RequestBody final String barcode) {
        log.debug("REST request to save Checkin : {}", barcode);
        BorrowHistory history = borrowHistoryRepository.getByItemBarcode(barcode);
        
        if (history!=null) {
        	checkin(history);
        }
        
    }
    
    /**
     * POST  /rest/checkin -> Create a new borrowHistory.
     */
    @RequestMapping(value = "/rest/checkin/id/{:id}", params={"callNumber"},
    		method = RequestMethod.PUT,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void checkInById(@PathVariable Long id) {
    	log.debug("REST request to save Checkin by history Id: {}", id);
    	BorrowHistory history = borrowHistoryRepository.getOne(id);
    	
    	if (history!=null) {
    		checkin(history);
    	}
    	
    }

    //Check in the item.
   private void checkin(BorrowHistory history) {
	   DateTime returnDate = new DateTime();
	   history.setReturnDate(returnDate);
	   history.setCleared(Boolean.TRUE);
	   borrowHistoryRepository.save(history); 
	   
	   Item item = history.getItem(); 
	   item.setStatus(ItemStatus.ReadyForBorrow.getValue());
	   itemRepository.save(item);
	   
   }
    
}
