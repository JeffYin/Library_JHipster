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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import com.dream.books.Application;
import com.dream.books.domain.BorrowHistory;
import com.dream.books.repository.BorrowHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BorrowHistoryResource REST controller.
 *
 * @see BorrowHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BorrowHistoryResourceTest {
   private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

   private static final DateTime DEFAULT_BORROW_DATE = new DateTime(0L);
   private static final DateTime UPDATED_BORROW_DATE = new DateTime().withMillisOfSecond(0);
   private static final String DEFAULT_BORROW_DATE_STR = dateTimeFormatter.print(DEFAULT_BORROW_DATE);
    
   private static final DateTime DEFAULT_RETURN_DATE = new DateTime(0L);
   private static final DateTime UPDATED_RETURN_DATE = new DateTime().withMillisOfSecond(0);
   private static final String DEFAULT_RETURN_DATE_STR = dateTimeFormatter.print(DEFAULT_RETURN_DATE);
    
    private static final Boolean DEFAULT_CLEARED = false;
    private static final Boolean UPDATED_CLEARED = true;
    private static final String DEFAULT_COMMENTS = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENTS = "UPDATED_TEXT";
    

    @Inject
    private BorrowHistoryRepository borrowHistoryRepository;

    private MockMvc restBorrowHistoryMockMvc;

    private BorrowHistory borrowHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BorrowHistoryResource borrowHistoryResource = new BorrowHistoryResource();
        ReflectionTestUtils.setField(borrowHistoryResource, "borrowHistoryRepository", borrowHistoryRepository);
        this.restBorrowHistoryMockMvc = MockMvcBuilders.standaloneSetup(borrowHistoryResource).build();
    }

    @Before
    public void initTest() {
        borrowHistory = new BorrowHistory();
        borrowHistory.setBorrowDate(DEFAULT_BORROW_DATE);
        borrowHistory.setReturnDate(DEFAULT_RETURN_DATE);
        borrowHistory.setCleared(DEFAULT_CLEARED);
        borrowHistory.setComments(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createBorrowHistory() throws Exception {
        // Validate the database is empty
        assertThat(borrowHistoryRepository.findAll()).hasSize(0);

        // Create the BorrowHistory
        restBorrowHistoryMockMvc.perform(post("/app/rest/borrowHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(borrowHistory)))
                .andExpect(status().isOk());

        // Validate the BorrowHistory in the database
        List<BorrowHistory> borrowHistorys = borrowHistoryRepository.findAll();
        assertThat(borrowHistorys).hasSize(1);
        BorrowHistory testBorrowHistory = borrowHistorys.iterator().next();
        assertThat(testBorrowHistory.getBorrowDate()).isEqualTo(DEFAULT_BORROW_DATE);
        assertThat(testBorrowHistory.getReturnDate()).isEqualTo(DEFAULT_RETURN_DATE);
        assertThat(testBorrowHistory.getCleared()).isEqualTo(DEFAULT_CLEARED);
        assertThat(testBorrowHistory.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllBorrowHistorys() throws Exception {
        // Initialize the database
        borrowHistoryRepository.saveAndFlush(borrowHistory);

        // Get all the borrowHistorys
        restBorrowHistoryMockMvc.perform(get("/app/rest/borrowHistorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(borrowHistory.getId().intValue()))
                .andExpect(jsonPath("$.[0].borrowDate").value(DEFAULT_BORROW_DATE_STR))
                .andExpect(jsonPath("$.[0].returnDate").value(DEFAULT_RETURN_DATE_STR))
                .andExpect(jsonPath("$.[0].cleared").value(DEFAULT_CLEARED.booleanValue()))
                .andExpect(jsonPath("$.[0].comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getBorrowHistory() throws Exception {
        // Initialize the database
        borrowHistoryRepository.saveAndFlush(borrowHistory);

        // Get the borrowHistory
        restBorrowHistoryMockMvc.perform(get("/app/rest/borrowHistorys/{id}", borrowHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(borrowHistory.getId().intValue()))
            .andExpect(jsonPath("$.borrowDate").value(DEFAULT_BORROW_DATE_STR))
            .andExpect(jsonPath("$.returnDate").value(DEFAULT_RETURN_DATE_STR))
            .andExpect(jsonPath("$.cleared").value(DEFAULT_CLEARED.booleanValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBorrowHistory() throws Exception {
        // Get the borrowHistory
        restBorrowHistoryMockMvc.perform(get("/app/rest/borrowHistorys/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBorrowHistory() throws Exception {
        // Initialize the database
        borrowHistoryRepository.saveAndFlush(borrowHistory);

        // Update the borrowHistory
        borrowHistory.setBorrowDate(UPDATED_BORROW_DATE);
        borrowHistory.setReturnDate(UPDATED_RETURN_DATE);
        borrowHistory.setCleared(UPDATED_CLEARED);
        borrowHistory.setComments(UPDATED_COMMENTS);
        restBorrowHistoryMockMvc.perform(post("/app/rest/borrowHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(borrowHistory)))
                .andExpect(status().isOk());

        // Validate the BorrowHistory in the database
        List<BorrowHistory> borrowHistorys = borrowHistoryRepository.findAll();
        assertThat(borrowHistorys).hasSize(1);
        BorrowHistory testBorrowHistory = borrowHistorys.iterator().next();
        assertThat(testBorrowHistory.getBorrowDate()).isEqualTo(UPDATED_BORROW_DATE);
        assertThat(testBorrowHistory.getReturnDate()).isEqualTo(UPDATED_RETURN_DATE);
        assertThat(testBorrowHistory.getCleared()).isEqualTo(UPDATED_CLEARED);
        assertThat(testBorrowHistory.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void deleteBorrowHistory() throws Exception {
        // Initialize the database
        borrowHistoryRepository.saveAndFlush(borrowHistory);

        // Get the borrowHistory
        restBorrowHistoryMockMvc.perform(delete("/app/rest/borrowHistorys/{id}", borrowHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BorrowHistory> borrowHistorys = borrowHistoryRepository.findAll();
        assertThat(borrowHistorys).hasSize(0);
    }
}
