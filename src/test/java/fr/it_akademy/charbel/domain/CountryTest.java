package fr.it_akademy.charbel.domain;

import static fr.it_akademy.charbel.domain.CharacterTestSamples.*;
import static fr.it_akademy.charbel.domain.CountryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.charbel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = getCountrySample1();
        Country country2 = new Country();
        assertThat(country1).isNotEqualTo(country2);

        country2.setId(country1.getId());
        assertThat(country1).isEqualTo(country2);

        country2 = getCountrySample2();
        assertThat(country1).isNotEqualTo(country2);
    }

    @Test
    void characterTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        Character characterBack = getCharacterRandomSampleGenerator();

        country.setCharacter(characterBack);
        assertThat(country.getCharacter()).isEqualTo(characterBack);
        assertThat(characterBack.getCountry()).isEqualTo(country);

        country.character(null);
        assertThat(country.getCharacter()).isNull();
        assertThat(characterBack.getCountry()).isNull();
    }
}
