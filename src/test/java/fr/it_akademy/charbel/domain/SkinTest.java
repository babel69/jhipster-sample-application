package fr.it_akademy.charbel.domain;

import static fr.it_akademy.charbel.domain.CharacterTestSamples.*;
import static fr.it_akademy.charbel.domain.SkinTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.charbel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SkinTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Skin.class);
        Skin skin1 = getSkinSample1();
        Skin skin2 = new Skin();
        assertThat(skin1).isNotEqualTo(skin2);

        skin2.setId(skin1.getId());
        assertThat(skin1).isEqualTo(skin2);

        skin2 = getSkinSample2();
        assertThat(skin1).isNotEqualTo(skin2);
    }

    @Test
    void characterTest() throws Exception {
        Skin skin = getSkinRandomSampleGenerator();
        Character characterBack = getCharacterRandomSampleGenerator();

        skin.setCharacter(characterBack);
        assertThat(skin.getCharacter()).isEqualTo(characterBack);
        assertThat(characterBack.getSkin()).isEqualTo(skin);

        skin.character(null);
        assertThat(skin.getCharacter()).isNull();
        assertThat(characterBack.getSkin()).isNull();
    }
}
