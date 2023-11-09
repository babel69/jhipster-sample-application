package fr.it_akademy.charbel.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.charbel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PowerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PowerDTO.class);
        PowerDTO powerDTO1 = new PowerDTO();
        powerDTO1.setId(1L);
        PowerDTO powerDTO2 = new PowerDTO();
        assertThat(powerDTO1).isNotEqualTo(powerDTO2);
        powerDTO2.setId(powerDTO1.getId());
        assertThat(powerDTO1).isEqualTo(powerDTO2);
        powerDTO2.setId(2L);
        assertThat(powerDTO1).isNotEqualTo(powerDTO2);
        powerDTO1.setId(null);
        assertThat(powerDTO1).isNotEqualTo(powerDTO2);
    }
}
