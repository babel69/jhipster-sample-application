package fr.it_akademy.charbel.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.charbel.IntegrationTest;
import fr.it_akademy.charbel.domain.Power;
import fr.it_akademy.charbel.repository.PowerRepository;
import fr.it_akademy.charbel.service.dto.PowerDTO;
import fr.it_akademy.charbel.service.mapper.PowerMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PowerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PowerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/powers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PowerRepository powerRepository;

    @Autowired
    private PowerMapper powerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPowerMockMvc;

    private Power power;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Power createEntity(EntityManager em) {
        Power power = new Power().name(DEFAULT_NAME);
        return power;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Power createUpdatedEntity(EntityManager em) {
        Power power = new Power().name(UPDATED_NAME);
        return power;
    }

    @BeforeEach
    public void initTest() {
        power = createEntity(em);
    }

    @Test
    @Transactional
    void createPower() throws Exception {
        int databaseSizeBeforeCreate = powerRepository.findAll().size();
        // Create the Power
        PowerDTO powerDTO = powerMapper.toDto(power);
        restPowerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(powerDTO)))
            .andExpect(status().isCreated());

        // Validate the Power in the database
        List<Power> powerList = powerRepository.findAll();
        assertThat(powerList).hasSize(databaseSizeBeforeCreate + 1);
        Power testPower = powerList.get(powerList.size() - 1);
        assertThat(testPower.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createPowerWithExistingId() throws Exception {
        // Create the Power with an existing ID
        power.setId(1L);
        PowerDTO powerDTO = powerMapper.toDto(power);

        int databaseSizeBeforeCreate = powerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPowerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(powerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Power in the database
        List<Power> powerList = powerRepository.findAll();
        assertThat(powerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPowers() throws Exception {
        // Initialize the database
        powerRepository.saveAndFlush(power);

        // Get all the powerList
        restPowerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(power.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getPower() throws Exception {
        // Initialize the database
        powerRepository.saveAndFlush(power);

        // Get the power
        restPowerMockMvc
            .perform(get(ENTITY_API_URL_ID, power.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(power.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingPower() throws Exception {
        // Get the power
        restPowerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPower() throws Exception {
        // Initialize the database
        powerRepository.saveAndFlush(power);

        int databaseSizeBeforeUpdate = powerRepository.findAll().size();

        // Update the power
        Power updatedPower = powerRepository.findById(power.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPower are not directly saved in db
        em.detach(updatedPower);
        updatedPower.name(UPDATED_NAME);
        PowerDTO powerDTO = powerMapper.toDto(updatedPower);

        restPowerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, powerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(powerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Power in the database
        List<Power> powerList = powerRepository.findAll();
        assertThat(powerList).hasSize(databaseSizeBeforeUpdate);
        Power testPower = powerList.get(powerList.size() - 1);
        assertThat(testPower.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPower() throws Exception {
        int databaseSizeBeforeUpdate = powerRepository.findAll().size();
        power.setId(longCount.incrementAndGet());

        // Create the Power
        PowerDTO powerDTO = powerMapper.toDto(power);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPowerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, powerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(powerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Power in the database
        List<Power> powerList = powerRepository.findAll();
        assertThat(powerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPower() throws Exception {
        int databaseSizeBeforeUpdate = powerRepository.findAll().size();
        power.setId(longCount.incrementAndGet());

        // Create the Power
        PowerDTO powerDTO = powerMapper.toDto(power);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPowerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(powerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Power in the database
        List<Power> powerList = powerRepository.findAll();
        assertThat(powerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPower() throws Exception {
        int databaseSizeBeforeUpdate = powerRepository.findAll().size();
        power.setId(longCount.incrementAndGet());

        // Create the Power
        PowerDTO powerDTO = powerMapper.toDto(power);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPowerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(powerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Power in the database
        List<Power> powerList = powerRepository.findAll();
        assertThat(powerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePowerWithPatch() throws Exception {
        // Initialize the database
        powerRepository.saveAndFlush(power);

        int databaseSizeBeforeUpdate = powerRepository.findAll().size();

        // Update the power using partial update
        Power partialUpdatedPower = new Power();
        partialUpdatedPower.setId(power.getId());

        partialUpdatedPower.name(UPDATED_NAME);

        restPowerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPower.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPower))
            )
            .andExpect(status().isOk());

        // Validate the Power in the database
        List<Power> powerList = powerRepository.findAll();
        assertThat(powerList).hasSize(databaseSizeBeforeUpdate);
        Power testPower = powerList.get(powerList.size() - 1);
        assertThat(testPower.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePowerWithPatch() throws Exception {
        // Initialize the database
        powerRepository.saveAndFlush(power);

        int databaseSizeBeforeUpdate = powerRepository.findAll().size();

        // Update the power using partial update
        Power partialUpdatedPower = new Power();
        partialUpdatedPower.setId(power.getId());

        partialUpdatedPower.name(UPDATED_NAME);

        restPowerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPower.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPower))
            )
            .andExpect(status().isOk());

        // Validate the Power in the database
        List<Power> powerList = powerRepository.findAll();
        assertThat(powerList).hasSize(databaseSizeBeforeUpdate);
        Power testPower = powerList.get(powerList.size() - 1);
        assertThat(testPower.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPower() throws Exception {
        int databaseSizeBeforeUpdate = powerRepository.findAll().size();
        power.setId(longCount.incrementAndGet());

        // Create the Power
        PowerDTO powerDTO = powerMapper.toDto(power);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPowerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, powerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(powerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Power in the database
        List<Power> powerList = powerRepository.findAll();
        assertThat(powerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPower() throws Exception {
        int databaseSizeBeforeUpdate = powerRepository.findAll().size();
        power.setId(longCount.incrementAndGet());

        // Create the Power
        PowerDTO powerDTO = powerMapper.toDto(power);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPowerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(powerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Power in the database
        List<Power> powerList = powerRepository.findAll();
        assertThat(powerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPower() throws Exception {
        int databaseSizeBeforeUpdate = powerRepository.findAll().size();
        power.setId(longCount.incrementAndGet());

        // Create the Power
        PowerDTO powerDTO = powerMapper.toDto(power);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPowerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(powerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Power in the database
        List<Power> powerList = powerRepository.findAll();
        assertThat(powerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePower() throws Exception {
        // Initialize the database
        powerRepository.saveAndFlush(power);

        int databaseSizeBeforeDelete = powerRepository.findAll().size();

        // Delete the power
        restPowerMockMvc
            .perform(delete(ENTITY_API_URL_ID, power.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Power> powerList = powerRepository.findAll();
        assertThat(powerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
