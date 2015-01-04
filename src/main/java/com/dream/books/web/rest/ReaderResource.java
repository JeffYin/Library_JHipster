package com.dream.books.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dream.books.domain.Reader;
import com.dream.books.repository.ReaderRepository;

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
 * REST controller for managing Reader.
 */
@RestController
@RequestMapping("/app")
public class ReaderResource {

    private final Logger log = LoggerFactory.getLogger(ReaderResource.class);

    @Inject
    private ReaderRepository readerRepository;

    /**
     * POST  /rest/readers -> Create a new reader.
     */
    @RequestMapping(value = "/rest/readers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Reader reader) {
        log.debug("REST request to save Reader : {}", reader);
        readerRepository.save(reader);
    }

    /**
     * GET  /rest/readers -> get all the readers.
     */
    @RequestMapping(value = "/rest/readers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Reader> getAll() {
        log.debug("REST request to get all Readers");
        return readerRepository.findAll();
    }
    
    
    /**
    * GET  /rest/readers -> get all the readers.
    */
   @RequestMapping(value = "/rest/readers", params={"name"},
           method = RequestMethod.GET,
           produces = MediaType.APPLICATION_JSON_VALUE)
   @Timed
   public List<Reader> getByName(@RequestParam String name) {
       log.debug("REST request to get all Readers");
       return readerRepository.getByNameLike(name);
   }
    

    /**
     * GET  /rest/readers/:id -> get the "id" reader.
     */
    @RequestMapping(value = "/rest/readers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Reader> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Reader : {}", id);
        Reader reader = readerRepository.findOne(id);
        if (reader == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reader, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/readers/:id -> delete the "id" reader.
     */
    @RequestMapping(value = "/rest/readers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Reader : {}", id);
        readerRepository.delete(id);
    }
}
