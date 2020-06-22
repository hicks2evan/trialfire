package hicks2evan.web.rest;

import hicks2evan.domain.Email;
import hicks2evan.repository.EmailRepository;
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
 * REST controller for managing {@link hicks2evan.domain.Email}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmailResource {

    private final Logger log = LoggerFactory.getLogger(EmailResource.class);

    private static final String ENTITY_NAME = "email";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailRepository emailRepository;

    public EmailResource(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    /**
     * {@code POST  /emails} : Create a new email.
     *
     * @param email the email to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new email, or with status {@code 400 (Bad Request)} if the email has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emails")
    public ResponseEntity<Email> createEmail(@RequestBody Email email) throws URISyntaxException {
        log.debug("REST request to save Email : {}", email);
        if (email.getId() != null) {
            throw new BadRequestAlertException("A new email cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Email result = emailRepository.save(email);
        return ResponseEntity.created(new URI("/api/emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emails} : Updates an existing email.
     *
     * @param email the email to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated email,
     * or with status {@code 400 (Bad Request)} if the email is not valid,
     * or with status {@code 500 (Internal Server Error)} if the email couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emails")
    public ResponseEntity<Email> updateEmail(@RequestBody Email email) throws URISyntaxException {
        log.debug("REST request to update Email : {}", email);
        if (email.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Email result = emailRepository.save(email);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, email.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emails} : get all the emails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emails in body.
     */
    @GetMapping("/emails")
    public List<Email> getAllEmails() {
        log.debug("REST request to get all Emails");
        return emailRepository.findAll();
    }

    /**
     * {@code GET  /emails/:id} : get the "id" email.
     *
     * @param id the id of the email to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the email, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emails/{id}")
    public ResponseEntity<Email> getEmail(@PathVariable Long id) {
        log.debug("REST request to get Email : {}", id);
        Optional<Email> email = emailRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(email);
    }

    /**
     * {@code DELETE  /emails/:id} : delete the "id" email.
     *
     * @param id the id of the email to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emails/{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable Long id) {
        log.debug("REST request to delete Email : {}", id);
        emailRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
