package hicks2evan.web.rest;

import hicks2evan.domain.Trial;
import hicks2evan.repository.TrialRepository;
import hicks2evan.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link hicks2evan.domain.Trial}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TrialResource {

    private final Logger log = LoggerFactory.getLogger(TrialResource.class);

    private static final String ENTITY_NAME = "trial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrialRepository trialRepository;

    public TrialResource(TrialRepository trialRepository) {
        this.trialRepository = trialRepository;
    }

    /**
     * {@code POST  /trials} : Create a new trial.
     *
     * @param trial the trial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trial, or with status {@code 400 (Bad Request)} if the trial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trials")
    public ResponseEntity<Trial> createTrial(@RequestBody Trial trial) throws URISyntaxException {
        log.debug("REST request to save Trial : {}", trial);
        if (trial.getId() != null) {
            throw new BadRequestAlertException("A new trial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Trial result = trialRepository.save(trial);
        return ResponseEntity.created(new URI("/api/trials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trials} : Updates an existing trial.
     *
     * @param trial the trial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trial,
     * or with status {@code 400 (Bad Request)} if the trial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trials")
    public ResponseEntity<Trial> updateTrial(@RequestBody Trial trial) throws URISyntaxException {
        log.debug("REST request to update Trial : {}", trial);
        if (trial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Trial result = trialRepository.save(trial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trial.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trials} : get all the trials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trials in body.
     */
    @GetMapping("/trials")
    public List<Trial> getAllTrials() {
        log.debug("REST request to get all Trials");
        return trialRepository.findAll();
    }

    /**
     * {@code GET  /trials/:id} : get the "id" trial.
     *
     * @param id the id of the trial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trials/{id}")
    public ResponseEntity<Trial> getTrial(@PathVariable Long id) {
        log.debug("REST request to get Trial : {}", id);
        Optional<Trial> trial = trialRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trial);
    }

    /**
     * {@code DELETE  /trials/:id} : delete the "id" trial.
     *
     * @param id the id of the trial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trials/{id}")
    public ResponseEntity<Void> deleteTrial(@PathVariable Long id) {
        log.debug("REST request to delete Trial : {}", id);
        trialRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
