package fr.it_akademy.charbel.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.charbel.domain.Skin} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SkinDTO implements Serializable {

    private Long id;

    private String color;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkinDTO)) {
            return false;
        }

        SkinDTO skinDTO = (SkinDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, skinDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkinDTO{" +
            "id=" + getId() +
            ", color='" + getColor() + "'" +
            "}";
    }
}
