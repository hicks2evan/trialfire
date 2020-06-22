package hicks2evan.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A Trial.
 */
@Entity
@Table(name = "trial")
public class Trial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private Float price;

    @Column(name = "user")
    private Integer user;

    @Column(name = "increasedprice")
    private Float increasedprice;

    @Column(name = "startdate")
    private Instant startdate;

    @Column(name = "enddate")
    private Instant enddate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Trial name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public Trial status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getPrice() {
        return price;
    }

    public Trial price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getUser() {
        return user;
    }

    public Trial user(Integer user) {
        this.user = user;
        return this;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Float getIncreasedprice() {
        return increasedprice;
    }

    public Trial increasedprice(Float increasedprice) {
        this.increasedprice = increasedprice;
        return this;
    }

    public void setIncreasedprice(Float increasedprice) {
        this.increasedprice = increasedprice;
    }

    public Instant getStartdate() {
        return startdate;
    }

    public Trial startdate(Instant startdate) {
        this.startdate = startdate;
        return this;
    }

    public void setStartdate(Instant startdate) {
        this.startdate = startdate;
    }

    public Instant getEnddate() {
        return enddate;
    }

    public Trial enddate(Instant enddate) {
        this.enddate = enddate;
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trial)) {
            return false;
        }
        return id != null && id.equals(((Trial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Trial{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", price=" + getPrice() +
            ", user=" + getUser() +
            ", increasedprice=" + getIncreasedprice() +
            ", startdate='" + getStartdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            "}";
    }
}
