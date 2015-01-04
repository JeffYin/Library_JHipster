package com.dream.books.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dream.books.domain.ReaderCard;
import com.dream.books.repository.ReaderCardRepository;

import org.apache.commons.lang3.StringUtils;
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
 * REST controller for managing ReaderCard.
 */
@RestController
@RequestMapping("/app")
public class ReaderCardResource {

    private final Logger log = LoggerFactory.getLogger(ReaderCardResource.class);

    @Inject
    private ReaderCardRepository readerCardRepository;

    /**
     * POST  /rest/readerCards -> Create a new readerCard.
     */
    @RequestMapping(value = "/rest/readerCards",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody ReaderCard readerCard) {
        log.debug("REST request to save ReaderCard : {}", readerCard);
        readerCardRepository.save(readerCard);
    }

    /**
     * GET  /rest/readerCards -> get all the readerCards.
     */
    @RequestMapping(value = "/rest/readerCards",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ReaderCard> getAll(@RequestParam(required = false) final String barcode) {
        log.debug("REST request to get all ReaderCards");
        return readerCardRepository.findAll();
    }

    /**
     * GET  /rest/readerCards?barcode=xxxx -> get the readerCard by the given barcode.
     */
    @RequestMapping(value = "/rest/readerCards", params={"barcode"},
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReaderCard> getByBarcode(@RequestParam String barcode, HttpServletResponse response) {
        log.debug("REST request to get ReaderCard : {}", barcode);
        List<ReaderCard> readerCardList = readerCardRepository.findByBarcode(barcode); 
        if (readerCardList.size()==1){
        	ReaderCard readerCard = readerCardList.get(0); 
        	 return new ResponseEntity<>(readerCard, HttpStatus.OK);
        } else  if (readerCardList.size()==0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
        	 return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
       
    }

    /**
     * GET  /rest/readerCards/:id -> get the "id" readerCard.
     */
    @RequestMapping(value = "/rest/readerCards/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReaderCard> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get ReaderCard : {}", id);
        ReaderCard readerCard = readerCardRepository.findOne(id); 
        if (readerCard == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(readerCard, HttpStatus.OK);
    }

       
    /**
     * DELETE  /rest/readerCards/:id -> delete the "id" readerCard.
     */
    @RequestMapping(value = "/rest/readerCards/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ReaderCard : {}", id);
        readerCardRepository.delete(id);
    }
}
