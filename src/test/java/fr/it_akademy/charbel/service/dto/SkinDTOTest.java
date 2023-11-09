package fr.it_akademy.charbel.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.charbel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SkinDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkinDTO.class);
        SkinDTO skinDTO1 = new SkinDTO();
        skinDTO1.setId(1L);
        SkinDTO skinDTO2 = new SkinDTO();
        assertThat(skinDTO1).isNotEqualTo(skinDTO2);
        skinDTO2.setId(skinDTO1.getId());
        assertThat(skinDTO1).isEqualTo(skinDTO2);
        skinDTO2.setId(2L);
        assertThat(skinDTO1).isNotEqualTo(skinDTO2);
        skinDTO1.setId(null);
        assertThat(skinDTO1).isNotEqualTo(skinDTO2);
    }
}
