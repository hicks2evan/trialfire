package hicks2evan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Email.
 */
@Entity
@Table(name = "email")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sentdate")
    private Instant sentdate;

    @ManyToOne
    @JsonIgnoreProperties(value = "emails", allowSetters = true)
    private Trial trial;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSentdate() {
        return sentdate;
    }

    public Email sentdate(Instant sentdate) {
        this.sentdate = sentdate;
        return this;
    }

    public void setSentdate(Instant sentdate) {
        this.sentdate = sentdate;
    }

    public Trial getTrial() {
        return trial;
    }

    public Email trial(Trial trial) {
        this.trial = trial;
        return this;
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email)) {
            return false;
        }
        return id != null && id.equals(((Email) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Email{" +
            "id=" + getId() +
            ", sentdate='" + getSentdate() + "'" +
            "}";
    }
}
