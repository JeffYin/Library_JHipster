package com.dream.books.web.rest;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

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
import com.dream.books.domain.Bibliograph;
import com.dream.books.repository.BibliographRepository;

/**
 * REST controller for managing Bibliograph.
 */
@RestController
@RequestMapping("/app")
public class BibliographResource {

    private final Logger log = LoggerFactory.getLogger(BibliographResource.class);

    @Inject
    private BibliographRepository bibliographRepository;

    /**
     * POST  /rest/bibliographs -> Create a new bibliograph.
     */
    @RequestMapping(value = "/rest/bibliographs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Bibliograph bibliograph) {
        log.debug("REST request to save Bibliograph : {}", bibliograph);
        bibliographRepository.save(bibliograph);
    }

    /**
     * GET  /rest/bibliographs -> get all the bibliographs.
     */
    @RequestMapping(value = "/rest/bibliographs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Bibliograph> getAll() {
        log.debug("REST request to get all Bibliographs");
        return bibliographRepository.findAll();
    }

   
    /**
     * GET  /rest/bibliographs/:id -> get the "id" bibliograph.
     */
    @RequestMapping(value = "/rest/bibliographs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bibliograph> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Bibliograph : {}", id);
        Bibliograph bibliograph = bibliographRepository.findOneWithEagerRelationships(id);
        if (bibliograph == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bibliograph, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/bibliographs/:id -> delete the "id" bibliograph.
     */
    @RequestMapping(value = "/rest/bibliographs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Bibliograph : {}", id);
        bibliographRepository.delete(id);
    }
}
