package fr.it_akademy.charbel.domain;

import static fr.it_akademy.charbel.domain.CharacterTestSamples.*;
import static fr.it_akademy.charbel.domain.JobTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.charbel.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Job.class);
        Job job1 = getJobSample1();
        Job job2 = new Job();
        assertThat(job1).isNotEqualTo(job2);

        job2.setId(job1.getId());
        assertThat(job1).isEqualTo(job2);

        job2 = getJobSample2();
        assertThat(job1).isNotEqualTo(job2);
    }

    @Test
    void characterTest() throws Exception {
        Job job = getJobRandomSampleGenerator();
        Character characterBack = getCharacterRandomSampleGenerator();

        job.setCharacter(characterBack);
        assertThat(job.getCharacter()).isEqualTo(characterBack);
        assertThat(characterBack.getJob()).isEqualTo(job);

        job.character(null);
        assertThat(job.getCharacter()).isNull();
        assertThat(characterBack.getJob()).isNull();
    }
}
