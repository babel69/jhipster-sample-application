package fr.it_akademy.charbel.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.charbel.domain.Character} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CharacterDTO implements Serializable {

    private Long id;

    private String name;

    private String surname;

    private String countryName;

    private String jobName;

    private String powerName;

    private Integer age;

    private JobDTO job;

    private CountryDTO country;

    private PowerDTO power;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public JobDTO getJob() {
        return job;
    }

    public void setJob(JobDTO job) {
        this.job = job;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public PowerDTO getPower() {
        return power;
    }

    public void setPower(PowerDTO power) {
        this.power = power;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CharacterDTO)) {
            return false;
        }

        CharacterDTO characterDTO = (CharacterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, characterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CharacterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", countryName='" + getCountryName() + "'" +
            ", jobName='" + getJobName() + "'" +
            ", powerName='" + getPowerName() + "'" +
            ", age=" + getAge() +
            ", job=" + getJob() +
            ", country=" + getCountry() +
            ", power=" + getPower() +
            "}";
    }
}
