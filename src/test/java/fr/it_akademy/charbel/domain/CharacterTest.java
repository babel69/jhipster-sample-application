package fr.it_akademy.charbel.domain;

import static fr.it_akademy.charbel.domain.CharacterTestSamples.*;
import static fr.it_akademy.charbel.domain.CountryTestSamples.*;
import static fr.it_akademy.charbel.domain.JobTestSamples.*;
import static fr.it_akademy.charbel.domain.PowerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.charbel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CharacterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Character.class);
        Character character1 = getCharacterSample1();
        Character character2 = new Character();
        assertThat(character1).isNotEqualTo(character2);

        character2.setId(character1.getId());
        assertThat(character1).isEqualTo(character2);

        character2 = getCharacterSample2();
        assertThat(character1).isNotEqualTo(character2);
    }

    @Test
    void jobTest() throws Exception {
        Character character = getCharacterRandomSampleGenerator();
        Job jobBack = getJobRandomSampleGenerator();

        character.setJob(jobBack);
        assertThat(character.getJob()).isEqualTo(jobBack);

        character.job(null);
        assertThat(character.getJob()).isNull();
    }

    @Test
    void countryTest() throws Exception {
        Character character = getCharacterRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        character.setCountry(countryBack);
        assertThat(character.getCountry()).isEqualTo(countryBack);

        character.country(null);
        assertThat(character.getCountry()).isNull();
    }

    @Test
    void powerTest() throws Exception {
        Character character = getCharacterRandomSampleGenerator();
        Power powerBack = getPowerRandomSampleGenerator();

        character.setPower(powerBack);
        assertThat(character.getPower()).isEqualTo(powerBack);

        character.power(null);
        assertThat(character.getPower()).isNull();
    }
}
