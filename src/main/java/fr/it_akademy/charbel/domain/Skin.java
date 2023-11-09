package fr.it_akademy.charbel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Skin.
 */
@Entity
@Table(name = "skin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Skin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "color")
    private String color;

    @JsonIgnoreProperties(value = { "job", "country", "power", "skin" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "skin")
    private Character character;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Skin id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return this.color;
    }

    public Skin color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Character getCharacter() {
        return this.character;
    }

    public void setCharacter(Character character) {
        if (this.character != null) {
            this.character.setSkin(null);
        }
        if (character != null) {
            character.setSkin(this);
        }
        this.character = character;
    }

    public Skin character(Character character) {
        this.setCharacter(character);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skin)) {
            return false;
        }
        return getId() != null && getId().equals(((Skin) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Skin{" +
            "id=" + getId() +
            ", color='" + getColor() + "'" +
            "}";
    }
}
