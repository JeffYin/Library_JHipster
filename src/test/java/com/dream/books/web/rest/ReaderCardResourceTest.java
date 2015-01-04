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
import com.dream.books.domain.ReaderCard;
import com.dream.books.repository.ReaderCardRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReaderCardResource REST controller.
 *
 * @see ReaderCardResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ReaderCardResourceTest {

    private static final String DEFAULT_BARCODE = "SAMPLE_TEXT";
    private static final String UPDATED_BARCODE = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_STATUS = 0;
    private static final Integer UPDATED_STATUS = 1;
    

    @Inject
    private ReaderCardRepository readerCardRepository;

    private MockMvc restReaderCardMockMvc;

    private ReaderCard readerCard;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReaderCardResource readerCardResource = new ReaderCardResource();
        ReflectionTestUtils.setField(readerCardResource, "readerCardRepository", readerCardRepository);
        this.restReaderCardMockMvc = MockMvcBuilders.standaloneSetup(readerCardResource).build();
    }

    @Before
    public void initTest() {
        readerCard = new ReaderCard();
        readerCard.setBarcode(DEFAULT_BARCODE);
        readerCard.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createReaderCard() throws Exception {
        // Validate the database is empty
        assertThat(readerCardRepository.findAll()).hasSize(0);

        // Create the ReaderCard
        restReaderCardMockMvc.perform(post("/app/rest/readerCards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(readerCard)))
                .andExpect(status().isOk());

        // Validate the ReaderCard in the database
        List<ReaderCard> readerCards = readerCardRepository.findAll();
        assertThat(readerCards).hasSize(1);
        ReaderCard testReaderCard = readerCards.iterator().next();
        assertThat(testReaderCard.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testReaderCard.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllReaderCards() throws Exception {
        // Initialize the database
        readerCardRepository.saveAndFlush(readerCard);

        // Get all the readerCards
        restReaderCardMockMvc.perform(get("/app/rest/readerCards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(readerCard.getId().intValue()))
                .andExpect(jsonPath("$.[0].barcode").value(DEFAULT_BARCODE.toString()))
                .andExpect(jsonPath("$.[0].status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getReaderCard() throws Exception {
        // Initialize the database
        readerCardRepository.saveAndFlush(readerCard);

        // Get the readerCard
        restReaderCardMockMvc.perform(get("/app/rest/readerCards/{id}", readerCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(readerCard.getId().intValue()))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingReaderCard() throws Exception {
        // Get the readerCard
        restReaderCardMockMvc.perform(get("/app/rest/readerCards/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReaderCard() throws Exception {
        // Initialize the database
        readerCardRepository.saveAndFlush(readerCard);

        // Update the readerCard
        readerCard.setBarcode(UPDATED_BARCODE);
        readerCard.setStatus(UPDATED_STATUS);
        restReaderCardMockMvc.perform(post("/app/rest/readerCards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(readerCard)))
                .andExpect(status().isOk());

        // Validate the ReaderCard in the database
        List<ReaderCard> readerCards = readerCardRepository.findAll();
        assertThat(readerCards).hasSize(1);
        ReaderCard testReaderCard = readerCards.iterator().next();
        assertThat(testReaderCard.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testReaderCard.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteReaderCard() throws Exception {
        // Initialize the database
        readerCardRepository.saveAndFlush(readerCard);

        // Get the readerCard
        restReaderCardMockMvc.perform(delete("/app/rest/readerCards/{id}", readerCard.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ReaderCard> readerCards = readerCardRepository.findAll();
        assertThat(readerCards).hasSize(0);
    }
}
