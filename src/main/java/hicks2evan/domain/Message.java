package hicks2evan.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "sendafter")
    private Integer sendafter;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public Message subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getSendafter() {
        return sendafter;
    }

    public Message sendafter(Integer sendafter) {
        this.sendafter = sendafter;
        return this;
    }

    public void setSendafter(Integer sendafter) {
        this.sendafter = sendafter;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", sendafter=" + getSendafter() +
            "}";
    }
}
