package hicks2evan.web.rest;

import hicks2evan.TrialfireApp;
import hicks2evan.domain.Trial;
import hicks2evan.repository.TrialRepository;

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
 * Integration tests for the {@link TrialResource} REST controller.
 */
@SpringBootTest(classes = TrialfireApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TrialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final Integer DEFAULT_USER = 1;
    private static final Integer UPDATED_USER = 2;

    private static final Float DEFAULT_INCREASEDPRICE = 1F;
    private static final Float UPDATED_INCREASEDPRICE = 2F;

    private static final Instant DEFAULT_STARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrialMockMvc;

    private Trial trial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trial createEntity(EntityManager em) {
        Trial trial = new Trial()
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .price(DEFAULT_PRICE)
            .user(DEFAULT_USER)
            .increasedprice(DEFAULT_INCREASEDPRICE)
            .startdate(DEFAULT_STARTDATE)
            .enddate(DEFAULT_ENDDATE);
        return trial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trial createUpdatedEntity(EntityManager em) {
        Trial trial = new Trial()
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .price(UPDATED_PRICE)
            .user(UPDATED_USER)
            .increasedprice(UPDATED_INCREASEDPRICE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE);
        return trial;
    }

    @BeforeEach
    public void initTest() {
        trial = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrial() throws Exception {
        int databaseSizeBeforeCreate = trialRepository.findAll().size();

        // Create the Trial
        restTrialMockMvc.perform(post("/api/trials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trial)))
            .andExpect(status().isCreated());

        // Validate the Trial in the database
        List<Trial> trialList = trialRepository.findAll();
        assertThat(trialList).hasSize(databaseSizeBeforeCreate + 1);
        Trial testTrial = trialList.get(trialList.size() - 1);
        assertThat(testTrial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrial.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrial.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTrial.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testTrial.getIncreasedprice()).isEqualTo(DEFAULT_INCREASEDPRICE);
        assertThat(testTrial.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testTrial.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
    }

    @Test
    @Transactional
    public void createTrialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trialRepository.findAll().size();

        // Create the Trial with an existing ID
        trial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrialMockMvc.perform(post("/api/trials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trial)))
            .andExpect(status().isBadRequest());

        // Validate the Trial in the database
        List<Trial> trialList = trialRepository.findAll();
        assertThat(trialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTrials() throws Exception {
        // Initialize the database
        trialRepository.saveAndFlush(trial);

        // Get all the trialList
        restTrialMockMvc.perform(get("/api/trials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].increasedprice").value(hasItem(DEFAULT_INCREASEDPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTrial() throws Exception {
        // Initialize the database
        trialRepository.saveAndFlush(trial);

        // Get the trial
        restTrialMockMvc.perform(get("/api/trials/{id}", trial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trial.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER))
            .andExpect(jsonPath("$.increasedprice").value(DEFAULT_INCREASEDPRICE.doubleValue()))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrial() throws Exception {
        // Get the trial
        restTrialMockMvc.perform(get("/api/trials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrial() throws Exception {
        // Initialize the database
        trialRepository.saveAndFlush(trial);

        int databaseSizeBeforeUpdate = trialRepository.findAll().size();

        // Update the trial
        Trial updatedTrial = trialRepository.findById(trial.getId()).get();
        // Disconnect from session so that the updates on updatedTrial are not directly saved in db
        em.detach(updatedTrial);
        updatedTrial
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .price(UPDATED_PRICE)
            .user(UPDATED_USER)
            .increasedprice(UPDATED_INCREASEDPRICE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE);

        restTrialMockMvc.perform(put("/api/trials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrial)))
            .andExpect(status().isOk());

        // Validate the Trial in the database
        List<Trial> trialList = trialRepository.findAll();
        assertThat(trialList).hasSize(databaseSizeBeforeUpdate);
        Trial testTrial = trialList.get(trialList.size() - 1);
        assertThat(testTrial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrial.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrial.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTrial.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testTrial.getIncreasedprice()).isEqualTo(UPDATED_INCREASEDPRICE);
        assertThat(testTrial.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testTrial.getEnddate()).isEqualTo(UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTrial() throws Exception {
        int databaseSizeBeforeUpdate = trialRepository.findAll().size();

        // Create the Trial

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrialMockMvc.perform(put("/api/trials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trial)))
            .andExpect(status().isBadRequest());

        // Validate the Trial in the database
        List<Trial> trialList = trialRepository.findAll();
        assertThat(trialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrial() throws Exception {
        // Initialize the database
        trialRepository.saveAndFlush(trial);

        int databaseSizeBeforeDelete = trialRepository.findAll().size();

        // Delete the trial
        restTrialMockMvc.perform(delete("/api/trials/{id}", trial.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trial> trialList = trialRepository.findAll();
        assertThat(trialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
