package fr.it_akademy.charbel.domain;

import static fr.it_akademy.charbel.domain.CharacterTestSamples.*;
import static fr.it_akademy.charbel.domain.PowerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.charbel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PowerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Power.class);
        Power power1 = getPowerSample1();
        Power power2 = new Power();
        assertThat(power1).isNotEqualTo(power2);

        power2.setId(power1.getId());
        assertThat(power1).isEqualTo(power2);

        power2 = getPowerSample2();
        assertThat(power1).isNotEqualTo(power2);
    }

    @Test
    void characterTest() throws Exception {
        Power power = getPowerRandomSampleGenerator();
        Character characterBack = getCharacterRandomSampleGenerator();

        power.setCharacter(characterBack);
        assertThat(power.getCharacter()).isEqualTo(characterBack);
        assertThat(characterBack.getPower()).isEqualTo(power);

        power.character(null);
        assertThat(power.getCharacter()).isNull();
        assertThat(characterBack.getPower()).isNull();
    }
}
