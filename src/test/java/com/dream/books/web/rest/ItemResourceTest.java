package com.dream.books.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import com.dream.books.Application;
import com.dream.books.domain.Item;
import com.dream.books.repository.ItemRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ItemResource REST controller.
 *
 * @see ItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ItemResourceTest {

    private static final String DEFAULT_BARCODE = "SAMPLE_TEXT";
    private static final String UPDATED_BARCODE = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_STATUS = 0;
    private static final Integer UPDATED_STATUS = 1;
    
    private static final String DEFAULT_COMMENTS = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENTS = "UPDATED_TEXT";
    

    @Inject
    private ItemRepository itemRepository;

    private MockMvc restItemMockMvc;

    private Item item;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemResource itemResource = new ItemResource();
        ReflectionTestUtils.setField(itemResource, "itemRepository", itemRepository);
        this.restItemMockMvc = MockMvcBuilders.standaloneSetup(itemResource).build();
    }

    @Before
    public void initTest() {
        item = new Item();
        item.setBarcode(DEFAULT_BARCODE);
        item.setStatus(DEFAULT_STATUS);
        item.setComments(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createItem() throws Exception {
        // Validate the database is empty
        assertThat(itemRepository.findAll()).hasSize(0);

        // Create the Item
        restItemMockMvc.perform(post("/app/rest/items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andExpect(status().isOk());

        // Validate the Item in the database
        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(1);
        Item testItem = items.iterator().next();
        assertThat(testItem.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testItem.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testItem.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllItems() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the items
        restItemMockMvc.perform(get("/app/rest/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(item.getId().intValue()))
                .andExpect(jsonPath("$.[0].barcode").value(DEFAULT_BARCODE.toString()))
                .andExpect(jsonPath("$.[0].status").value(DEFAULT_STATUS))
                .andExpect(jsonPath("$.[0].comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get the item
        restItemMockMvc.perform(get("/app/rest/items/{id}", item.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(item.getId().intValue()))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingItem() throws Exception {
        // Get the item
        restItemMockMvc.perform(get("/app/rest/items/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Update the item
        item.setBarcode(UPDATED_BARCODE);
        item.setStatus(UPDATED_STATUS);
        item.setComments(UPDATED_COMMENTS);
        restItemMockMvc.perform(post("/app/rest/items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andExpect(status().isOk());

        // Validate the Item in the database
        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(1);
        Item testItem = items.iterator().next();
        assertThat(testItem.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testItem.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testItem.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void deleteItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get the item
        restItemMockMvc.perform(delete("/app/rest/items/{id}", item.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(0);
    }
}
