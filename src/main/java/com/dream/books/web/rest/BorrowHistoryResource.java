package com.dream.books.web.rest;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.dream.books.domain.BorrowHistory;
import com.dream.books.domain.Item;
import com.dream.books.domain.Reader;
import com.dream.books.repository.BorrowHistoryRepository;

/**
 * REST controller for managing BorrowHistory.
 */
@RestController
@RequestMapping("/app")
public class BorrowHistoryResource {

    private final Logger log = LoggerFactory.getLogger(BorrowHistoryResource.class);

    @Inject
    private BorrowHistoryRepository borrowHistoryRepository;

    /**
     * POST  /rest/borrowHistorys -> Create a new borrowHistory.
     */
    @RequestMapping(value = "/rest/borrowHistorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody String borrowHistory) {
        log.debug("REST request to save BorrowHistory : {}", borrowHistory);
        try {
			JSONObject jsonObject = new JSONObject(borrowHistory);
			Long readerId = new Long((Integer)jsonObject.get("readerId"));
			Reader reader = new Reader(); 
			reader.setId(readerId);
			
			JSONArray itemIds = (JSONArray) jsonObject.get("itemIds"); 
			for (int i=0; i<itemIds.length();i++) {
			   Long itemId = new Long((Integer)itemIds.get(0));
			   BorrowHistory history = new BorrowHistory();
			   
			   Item item = new Item();
			   item.setId(itemId);
			   
			   history.setReader(reader);
			   history.setItem(item);
			   
			   DateTime currentTime = DateTime.now();
			   history.setBorrowDate(DateTime.now());
			   
			   //TODO: set the return Date. 
			   history.setReturnDate(currentTime.plusDays(XXX));
			   
			   
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} 
        
//        borrowHistoryRepository.save(borrowHistory);
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
