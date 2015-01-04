package com.dream.books.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dream.books.domain.BorrowHistory;
import com.dream.books.repository.BorrowHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public void create(@RequestBody BorrowHistory borrowHistory) {
        log.debug("REST request to save BorrowHistory : {}", borrowHistory);
        borrowHistoryRepository.save(borrowHistory);
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
