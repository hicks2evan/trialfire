package hicks2evan.web.rest;

import hicks2evan.TrialfireApp;
import hicks2evan.domain.Email;
import hicks2evan.repository.EmailRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmailResource} REST controller.
 */
@SpringBootTest(classes = TrialfireApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmailResourceIT {

    private static final Instant DEFAULT_SENTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SENTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailMockMvc;

    private Email email;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Email createEntity(EntityManager em) {
        Email email = new Email()
            .sentdate(DEFAULT_SENTDATE);
        return email;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Email createUpdatedEntity(EntityManager em) {
        Email email = new Email()
            .sentdate(UPDATED_SENTDATE);
        return email;
    }

    @BeforeEach
    public void initTest() {
        email = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmail() throws Exception {
        int databaseSizeBeforeCreate = emailRepository.findAll().size();
        // Create the Email
        restEmailMockMvc.perform(post("/api/emails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(email)))
            .andExpect(status().isCreated());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeCreate + 1);
        Email testEmail = emailList.get(emailList.size() - 1);
        assertThat(testEmail.getSentdate()).isEqualTo(DEFAULT_SENTDATE);
    }

    @Test
    @Transactional
    public void createEmailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailRepository.findAll().size();

        // Create the Email with an existing ID
        email.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailMockMvc.perform(post("/api/emails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(email)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmails() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList
        restEmailMockMvc.perform(get("/api/emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(email.getId().intValue())))
            .andExpect(jsonPath("$.[*].sentdate").value(hasItem(DEFAULT_SENTDATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEmail() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get the email
        restEmailMockMvc.perform(get("/api/emails/{id}", email.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(email.getId().intValue()))
            .andExpect(jsonPath("$.sentdate").value(DEFAULT_SENTDATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmail() throws Exception {
        // Get the email
        restEmailMockMvc.perform(get("/api/emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmail() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        int databaseSizeBeforeUpdate = emailRepository.findAll().size();

        // Update the email
        Email updatedEmail = emailRepository.findById(email.getId()).get();
        // Disconnect from session so that the updates on updatedEmail are not directly saved in db
        em.detach(updatedEmail);
        updatedEmail
            .sentdate(UPDATED_SENTDATE);

        restEmailMockMvc.perform(put("/api/emails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmail)))
            .andExpect(status().isOk());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
        Email testEmail = emailList.get(emailList.size() - 1);
        assertThat(testEmail.getSentdate()).isEqualTo(UPDATED_SENTDATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmail() throws Exception {
        int databaseSizeBeforeUpdate = emailRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailMockMvc.perform(put("/api/emails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(email)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmail() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        int databaseSizeBeforeDelete = emailRepository.findAll().size();

        // Delete the email
        restEmailMockMvc.perform(delete("/api/emails/{id}", email.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
