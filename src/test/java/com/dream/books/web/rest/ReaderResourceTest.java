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
import com.dream.books.domain.Reader;
import com.dream.books.repository.ReaderRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReaderResource REST controller.
 *
 * @see ReaderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ReaderResourceTest {

    private static final String DEFAULT_PERMANENT_NO = "SAMPLE_TEXT";
    private static final String UPDATED_PERMANENT_NO = "UPDATED_TEXT";
    
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_HOME_PHONE = "SAMPLE_TEXT";
    private static final String UPDATED_HOME_PHONE = "UPDATED_TEXT";
    
    private static final String DEFAULT_MOBILE_PHONE = "SAMPLE_TEXT";
    private static final String UPDATED_MOBILE_PHONE = "UPDATED_TEXT";
    
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";
    
    private static final String DEFAULT_POST_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_POST_CODE = "UPDATED_TEXT";
    

    @Inject
    private ReaderRepository readerRepository;

    private MockMvc restReaderMockMvc;

    private Reader reader;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReaderResource readerResource = new ReaderResource();
        ReflectionTestUtils.setField(readerResource, "readerRepository", readerRepository);
        this.restReaderMockMvc = MockMvcBuilders.standaloneSetup(readerResource).build();
    }

    @Before
    public void initTest() {
        reader = new Reader();
        reader.setPermanentNo(DEFAULT_PERMANENT_NO);
        reader.setName(DEFAULT_NAME);
        reader.setHomePhone(DEFAULT_HOME_PHONE);
        reader.setMobilePhone(DEFAULT_MOBILE_PHONE);
        reader.setEmail(DEFAULT_EMAIL);
        reader.setAddress(DEFAULT_ADDRESS);
        reader.setPostCode(DEFAULT_POST_CODE);
    }

    @Test
    @Transactional
    public void createReader() throws Exception {
        // Validate the database is empty
        assertThat(readerRepository.findAll()).hasSize(0);

        // Create the Reader
        restReaderMockMvc.perform(post("/app/rest/readers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reader)))
                .andExpect(status().isOk());

        // Validate the Reader in the database
        List<Reader> readers = readerRepository.findAll();
        assertThat(readers).hasSize(1);
        Reader testReader = readers.iterator().next();
        assertThat(testReader.getPermanentNo()).isEqualTo(DEFAULT_PERMANENT_NO);
        assertThat(testReader.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReader.getHomePhone()).isEqualTo(DEFAULT_HOME_PHONE);
        assertThat(testReader.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testReader.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testReader.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testReader.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllReaders() throws Exception {
        // Initialize the database
        readerRepository.saveAndFlush(reader);

        // Get all the readers
        restReaderMockMvc.perform(get("/app/rest/readers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(reader.getId().intValue()))
                .andExpect(jsonPath("$.[0].permanentNo").value(DEFAULT_PERMANENT_NO.toString()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].homePhone").value(DEFAULT_HOME_PHONE.toString()))
                .andExpect(jsonPath("$.[0].mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
                .andExpect(jsonPath("$.[0].email").value(DEFAULT_EMAIL.toString()))
                .andExpect(jsonPath("$.[0].address").value(DEFAULT_ADDRESS.toString()))
                .andExpect(jsonPath("$.[0].postCode").value(DEFAULT_POST_CODE.toString()));
    }

    @Test
    @Transactional
    public void getReader() throws Exception {
        // Initialize the database
        readerRepository.saveAndFlush(reader);

        // Get the reader
        restReaderMockMvc.perform(get("/app/rest/readers/{id}", reader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(reader.getId().intValue()))
            .andExpect(jsonPath("$.permanentNo").value(DEFAULT_PERMANENT_NO.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.homePhone").value(DEFAULT_HOME_PHONE.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReader() throws Exception {
        // Get the reader
        restReaderMockMvc.perform(get("/app/rest/readers/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReader() throws Exception {
        // Initialize the database
        readerRepository.saveAndFlush(reader);

        // Update the reader
        reader.setPermanentNo(UPDATED_PERMANENT_NO);
        reader.setName(UPDATED_NAME);
        reader.setHomePhone(UPDATED_HOME_PHONE);
        reader.setMobilePhone(UPDATED_MOBILE_PHONE);
        reader.setEmail(UPDATED_EMAIL);
        reader.setAddress(UPDATED_ADDRESS);
        reader.setPostCode(UPDATED_POST_CODE);
        restReaderMockMvc.perform(post("/app/rest/readers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reader)))
                .andExpect(status().isOk());

        // Validate the Reader in the database
        List<Reader> readers = readerRepository.findAll();
        assertThat(readers).hasSize(1);
        Reader testReader = readers.iterator().next();
        assertThat(testReader.getPermanentNo()).isEqualTo(UPDATED_PERMANENT_NO);
        assertThat(testReader.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReader.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testReader.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testReader.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testReader.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testReader.getPostCode()).isEqualTo(UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void deleteReader() throws Exception {
        // Initialize the database
        readerRepository.saveAndFlush(reader);

        // Get the reader
        restReaderMockMvc.perform(delete("/app/rest/readers/{id}", reader.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Reader> readers = readerRepository.findAll();
        assertThat(readers).hasSize(0);
    }
}
