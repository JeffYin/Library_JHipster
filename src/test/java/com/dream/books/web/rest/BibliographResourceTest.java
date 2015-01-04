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
import com.dream.books.domain.Bibliograph;
import com.dream.books.repository.BibliographRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BibliographResource REST controller.
 *
 * @see BibliographResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BibliographResourceTest {

    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    
    private static final String DEFAULT_AUTHOR = "SAMPLE_TEXT";
    private static final String UPDATED_AUTHOR = "UPDATED_TEXT";
    
    private static final String DEFAULT_INTRO = "SAMPLE_TEXT";
    private static final String UPDATED_INTRO = "UPDATED_TEXT";
    
    private static final String DEFAULT_PUBLISHER = "SAMPLE_TEXT";
    private static final String UPDATED_PUBLISHER = "UPDATED_TEXT";
    
    private static final String DEFAULT_CALL_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_CALL_NUMBER = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_DUE_DAYS = 0;
    private static final Integer UPDATED_DUE_DAYS = 1;
    
    private static final String DEFAULT_IMAGE_URL = "SAMPLE_TEXT";
    private static final String UPDATED_IMAGE_URL = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_TYPE = 0;
    private static final Integer UPDATED_TYPE = 1;
    

    @Inject
    private BibliographRepository bibliographRepository;

    private MockMvc restBibliographMockMvc;

    private Bibliograph bibliograph;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BibliographResource bibliographResource = new BibliographResource();
        ReflectionTestUtils.setField(bibliographResource, "bibliographRepository", bibliographRepository);
        this.restBibliographMockMvc = MockMvcBuilders.standaloneSetup(bibliographResource).build();
    }

    @Before
    public void initTest() {
        bibliograph = new Bibliograph();
        bibliograph.setTitle(DEFAULT_TITLE);
        bibliograph.setAuthor(DEFAULT_AUTHOR);
        bibliograph.setIntro(DEFAULT_INTRO);
        bibliograph.setPublisher(DEFAULT_PUBLISHER);
        bibliograph.setCallNumber(DEFAULT_CALL_NUMBER);
        bibliograph.setDueDays(DEFAULT_DUE_DAYS);
        bibliograph.setImageUrl(DEFAULT_IMAGE_URL);
        bibliograph.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createBibliograph() throws Exception {
        // Validate the database is empty
        assertThat(bibliographRepository.findAll()).hasSize(0);

        // Create the Bibliograph
        restBibliographMockMvc.perform(post("/app/rest/bibliographs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bibliograph)))
                .andExpect(status().isOk());

        // Validate the Bibliograph in the database
        List<Bibliograph> bibliographs = bibliographRepository.findAll();
        assertThat(bibliographs).hasSize(1);
        Bibliograph testBibliograph = bibliographs.iterator().next();
        assertThat(testBibliograph.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBibliograph.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBibliograph.getIntro()).isEqualTo(DEFAULT_INTRO);
        assertThat(testBibliograph.getPublisher()).isEqualTo(DEFAULT_PUBLISHER);
        assertThat(testBibliograph.getCallNumber()).isEqualTo(DEFAULT_CALL_NUMBER);
        assertThat(testBibliograph.getDueDays()).isEqualTo(DEFAULT_DUE_DAYS);
        assertThat(testBibliograph.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testBibliograph.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void getAllBibliographs() throws Exception {
        // Initialize the database
        bibliographRepository.saveAndFlush(bibliograph);

        // Get all the bibliographs
        restBibliographMockMvc.perform(get("/app/rest/bibliographs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(bibliograph.getId().intValue()))
                .andExpect(jsonPath("$.[0].title").value(DEFAULT_TITLE.toString()))
                .andExpect(jsonPath("$.[0].author").value(DEFAULT_AUTHOR.toString()))
                .andExpect(jsonPath("$.[0].intro").value(DEFAULT_INTRO.toString()))
                .andExpect(jsonPath("$.[0].publisher").value(DEFAULT_PUBLISHER.toString()))
                .andExpect(jsonPath("$.[0].callNumber").value(DEFAULT_CALL_NUMBER.toString()))
                .andExpect(jsonPath("$.[0].dueDays").value(DEFAULT_DUE_DAYS))
                .andExpect(jsonPath("$.[0].imageUrl").value(DEFAULT_IMAGE_URL.toString()))
                .andExpect(jsonPath("$.[0].type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    public void getBibliograph() throws Exception {
        // Initialize the database
        bibliographRepository.saveAndFlush(bibliograph);

        // Get the bibliograph
        restBibliographMockMvc.perform(get("/app/rest/bibliographs/{id}", bibliograph.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bibliograph.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.intro").value(DEFAULT_INTRO.toString()))
            .andExpect(jsonPath("$.publisher").value(DEFAULT_PUBLISHER.toString()))
            .andExpect(jsonPath("$.callNumber").value(DEFAULT_CALL_NUMBER.toString()))
            .andExpect(jsonPath("$.dueDays").value(DEFAULT_DUE_DAYS))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingBibliograph() throws Exception {
        // Get the bibliograph
        restBibliographMockMvc.perform(get("/app/rest/bibliographs/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBibliograph() throws Exception {
        // Initialize the database
        bibliographRepository.saveAndFlush(bibliograph);

        // Update the bibliograph
        bibliograph.setTitle(UPDATED_TITLE);
        bibliograph.setAuthor(UPDATED_AUTHOR);
        bibliograph.setIntro(UPDATED_INTRO);
        bibliograph.setPublisher(UPDATED_PUBLISHER);
        bibliograph.setCallNumber(UPDATED_CALL_NUMBER);
        bibliograph.setDueDays(UPDATED_DUE_DAYS);
        bibliograph.setImageUrl(UPDATED_IMAGE_URL);
        bibliograph.setType(UPDATED_TYPE);
        restBibliographMockMvc.perform(post("/app/rest/bibliographs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bibliograph)))
                .andExpect(status().isOk());

        // Validate the Bibliograph in the database
        List<Bibliograph> bibliographs = bibliographRepository.findAll();
        assertThat(bibliographs).hasSize(1);
        Bibliograph testBibliograph = bibliographs.iterator().next();
        assertThat(testBibliograph.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBibliograph.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBibliograph.getIntro()).isEqualTo(UPDATED_INTRO);
        assertThat(testBibliograph.getPublisher()).isEqualTo(UPDATED_PUBLISHER);
        assertThat(testBibliograph.getCallNumber()).isEqualTo(UPDATED_CALL_NUMBER);
        assertThat(testBibliograph.getDueDays()).isEqualTo(UPDATED_DUE_DAYS);
        assertThat(testBibliograph.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testBibliograph.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteBibliograph() throws Exception {
        // Initialize the database
        bibliographRepository.saveAndFlush(bibliograph);

        // Get the bibliograph
        restBibliographMockMvc.perform(delete("/app/rest/bibliographs/{id}", bibliograph.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bibliograph> bibliographs = bibliographRepository.findAll();
        assertThat(bibliographs).hasSize(0);
    }
}
