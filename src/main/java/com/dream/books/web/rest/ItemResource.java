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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.dream.books.domain.Item;
import com.dream.books.repository.ItemRepository;

/**
 * REST controller for managing Item.
 */
@RestController
@RequestMapping("/app")
public class ItemResource {

    private final Logger log = LoggerFactory.getLogger(ItemResource.class);

    @Inject
    private ItemRepository itemRepository;

    /**
     * POST  /rest/items -> Create a new item.
     */
    @RequestMapping(value = "/rest/items",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Item item) {
        log.debug("REST request to save Item : {}", item);
        itemRepository.save(item);
    }

    /**
     * GET  /rest/items -> get all the items.
     */
    @RequestMapping(value = "/rest/items",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Item> getAll() {
        log.debug("REST request to get all Items");
        return itemRepository.findAll();
    }

    /**
     * GET  /rest/items?barcode=xxx -> get the item with the given barcode.
     */
    @RequestMapping(value = "/rest/items", params={"barcode"},
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Item> getByBarcode(@RequestParam final String barcode) {
    	log.debug("REST request to get the Item with the given barcode");
         List<Item> itemList = itemRepository.findByBarcode(barcode); 
         if (itemList.size()==1){
        	 Item readerCard = itemList.get(0); 
         	 return new ResponseEntity<>(readerCard, HttpStatus.OK);
         } else  if (itemList.size()==0) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         } else {
         	 return new ResponseEntity<>(HttpStatus.CONFLICT);
         }    }

    /**
     * GET  /rest/items?callNumber=xxxx -> get all the bibliographs including the callNumber.
     */
    @RequestMapping(value = "/rest/items", params={"callNumber"},
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Item> getByCallNumber(@RequestParam String callNumber) {
    	log.debug("REST request to get all Bibliographs that include the callNumber");
    	return itemRepository.getByCallNumberLike("%"+callNumber+"%");
    }

    /**
     * GET  /rest/items?title=xxxx -> get all the bibliographs including the callNumber.
     */
    @RequestMapping(value = "/rest/items", params={"title"},
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Item> getByTitle(@RequestParam String title) {
    	log.debug("REST request to get all Bibliographs which title includes the given value.");
    	return itemRepository.getByTitleLike("%"+title+"%");
    }

    
    /**
     * GET  /rest/items/:id -> get the "id" item.
     */
    @RequestMapping(value = "/rest/items/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Item> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Item : {}", id);
        Item item = itemRepository.findOne(id);
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/items/:id -> delete the "id" item.
     */
    @RequestMapping(value = "/rest/items/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Item : {}", id);
        itemRepository.delete(id);
    }
  }
