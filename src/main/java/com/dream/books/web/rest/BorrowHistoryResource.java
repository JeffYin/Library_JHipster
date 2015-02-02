package com.dream.books.web.rest;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.dream.books.data.ItemStatus;
import com.dream.books.domain.BorrowHistory;
import com.dream.books.domain.Item;
import com.dream.books.domain.Reader;
import com.dream.books.repository.BorrowHistoryRepository;
import com.dream.books.repository.ItemRepository;

/**
 * REST controller for managing BorrowHistory.
 */
@RestController
@RequestMapping("/app")
public class BorrowHistoryResource {
    private final Logger log = LoggerFactory.getLogger(BorrowHistoryResource.class);

    @Inject
    private BorrowHistoryRepository borrowHistoryRepository;
    
    @Inject
    private ItemRepository itemRepository; 

    /**
     * POST  /rest/borrowHistorys -> Create a new borrowHistory.
     */
    @RequestMapping(value = "/rest/borrowHistorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody CheckoutInfoWrapper wrapper) {
        log.debug("REST request to save BorrowHistory : {}", wrapper);
        List<Item>items = wrapper.getItems();
        Reader reader = wrapper.getReader(); 
        
        for (Item item:items) {
        	//Change Item status. 
        	
        	item.setStatus(ItemStatus.Borrowed.getValue());
        	itemRepository.save(item); 
        	
        	BorrowHistory history = new BorrowHistory();
        	
        	history.setReader(reader);
        	history.setItem(item);
        	
        	
        	DateTime currentTime = DateTime.now(); 
        	DateTime dueDate = currentTime.plusDays(item.getBibliograph().getDueDays());
        	
        	history.setBorrowDate(currentTime);
        	history.setDueDate(dueDate);
        	
        	history.setCleared(Boolean.FALSE);
        	borrowHistoryRepository.save(history);
        }
        
    }

    /**
     * GET  /rest/borrowHistorys -> get all the borrowHistorys.
     */
    @RequestMapping(value = "/rest/borrowHistorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BorrowHistory> getAll() {
        log.debug("REST request to get all BorrowHistorys");
        return borrowHistoryRepository.findAll();
    }

    /**
     * GET  /rest/borrowHistorys/:id -> get the "id" borrowHistory.
     */
    @RequestMapping(value = "/rest/borrowHistorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BorrowHistory> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get BorrowHistory : {}", id);
        BorrowHistory borrowHistory = borrowHistoryRepository.findOne(id);
        if (borrowHistory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(borrowHistory, HttpStatus.OK);
    }
    
    /**
     * GET  /rest/items?callNumber=xxxx -> get all the bibliographs including the callNumber.
     */
    @RequestMapping(value = "/rest/borrowHistorys", params={"callNumber"},
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BorrowHistory> getByCallNumber(@RequestParam String callNumber) {
    	log.debug("REST request to get all Bibliographs that include the callNumber");
    	return borrowHistoryRepository.getByCallNumberLike("%"+callNumber+"%");
    }

    /**
     * GET  /rest/items?title=xxxx -> get all the bibliographs including the callNumber.
     */
    @RequestMapping(value = "/rest/borrowHistorys", params={"title"},
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BorrowHistory> getByTitle(@RequestParam String title) {
    	log.debug("REST request to get all Bibliographs which title includes the given value.");
    	return borrowHistoryRepository.getByTitleLike("%"+title+"%");
    }

    /**
     * DELETE  /rest/borrowHistorys/:id -> delete the "id" borrowHistory.
     */
    @RequestMapping(value = "/rest/borrowHistorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete BorrowHistory : {}", id);
        borrowHistoryRepository.delete(id);
    }
}
