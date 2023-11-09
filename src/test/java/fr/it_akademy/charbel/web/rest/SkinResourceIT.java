package fr.it_akademy.charbel.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.charbel.IntegrationTest;
import fr.it_akademy.charbel.domain.Skin;
import fr.it_akademy.charbel.repository.SkinRepository;
import fr.it_akademy.charbel.service.dto.SkinDTO;
import fr.it_akademy.charbel.service.mapper.SkinMapper;
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
 * Integration tests for the {@link SkinResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SkinResourceIT {

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/skins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SkinRepository skinRepository;

    @Autowired
    private SkinMapper skinMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSkinMockMvc;

    private Skin skin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skin createEntity(EntityManager em) {
        Skin skin = new Skin().color(DEFAULT_COLOR);
        return skin;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skin createUpdatedEntity(EntityManager em) {
        Skin skin = new Skin().color(UPDATED_COLOR);
        return skin;
    }

    @BeforeEach
    public void initTest() {
        skin = createEntity(em);
    }

    @Test
    @Transactional
    void createSkin() throws Exception {
        int databaseSizeBeforeCreate = skinRepository.findAll().size();
        // Create the Skin
        SkinDTO skinDTO = skinMapper.toDto(skin);
        restSkinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skinDTO)))
            .andExpect(status().isCreated());

        // Validate the Skin in the database
        List<Skin> skinList = skinRepository.findAll();
        assertThat(skinList).hasSize(databaseSizeBeforeCreate + 1);
        Skin testSkin = skinList.get(skinList.size() - 1);
        assertThat(testSkin.getColor()).isEqualTo(DEFAULT_COLOR);
    }

    @Test
    @Transactional
    void createSkinWithExistingId() throws Exception {
        // Create the Skin with an existing ID
        skin.setId(1L);
        SkinDTO skinDTO = skinMapper.toDto(skin);

        int databaseSizeBeforeCreate = skinRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skinDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Skin in the database
        List<Skin> skinList = skinRepository.findAll();
        assertThat(skinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSkins() throws Exception {
        // Initialize the database
        skinRepository.saveAndFlush(skin);

        // Get all the skinList
        restSkinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skin.getId().intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)));
    }

    @Test
    @Transactional
    void getSkin() throws Exception {
        // Initialize the database
        skinRepository.saveAndFlush(skin);

        // Get the skin
        restSkinMockMvc
            .perform(get(ENTITY_API_URL_ID, skin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skin.getId().intValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR));
    }

    @Test
    @Transactional
    void getNonExistingSkin() throws Exception {
        // Get the skin
        restSkinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSkin() throws Exception {
        // Initialize the database
        skinRepository.saveAndFlush(skin);

        int databaseSizeBeforeUpdate = skinRepository.findAll().size();

        // Update the skin
        Skin updatedSkin = skinRepository.findById(skin.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSkin are not directly saved in db
        em.detach(updatedSkin);
        updatedSkin.color(UPDATED_COLOR);
        SkinDTO skinDTO = skinMapper.toDto(updatedSkin);

        restSkinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skinDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skinDTO))
            )
            .andExpect(status().isOk());

        // Validate the Skin in the database
        List<Skin> skinList = skinRepository.findAll();
        assertThat(skinList).hasSize(databaseSizeBeforeUpdate);
        Skin testSkin = skinList.get(skinList.size() - 1);
        assertThat(testSkin.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    void putNonExistingSkin() throws Exception {
        int databaseSizeBeforeUpdate = skinRepository.findAll().size();
        skin.setId(longCount.incrementAndGet());

        // Create the Skin
        SkinDTO skinDTO = skinMapper.toDto(skin);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skinDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skin in the database
        List<Skin> skinList = skinRepository.findAll();
        assertThat(skinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSkin() throws Exception {
        int databaseSizeBeforeUpdate = skinRepository.findAll().size();
        skin.setId(longCount.incrementAndGet());

        // Create the Skin
        SkinDTO skinDTO = skinMapper.toDto(skin);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skin in the database
        List<Skin> skinList = skinRepository.findAll();
        assertThat(skinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSkin() throws Exception {
        int databaseSizeBeforeUpdate = skinRepository.findAll().size();
        skin.setId(longCount.incrementAndGet());

        // Create the Skin
        SkinDTO skinDTO = skinMapper.toDto(skin);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skinDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Skin in the database
        List<Skin> skinList = skinRepository.findAll();
        assertThat(skinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSkinWithPatch() throws Exception {
        // Initialize the database
        skinRepository.saveAndFlush(skin);

        int databaseSizeBeforeUpdate = skinRepository.findAll().size();

        // Update the skin using partial update
        Skin partialUpdatedSkin = new Skin();
        partialUpdatedSkin.setId(skin.getId());

        partialUpdatedSkin.color(UPDATED_COLOR);

        restSkinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkin))
            )
            .andExpect(status().isOk());

        // Validate the Skin in the database
        List<Skin> skinList = skinRepository.findAll();
        assertThat(skinList).hasSize(databaseSizeBeforeUpdate);
        Skin testSkin = skinList.get(skinList.size() - 1);
        assertThat(testSkin.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    void fullUpdateSkinWithPatch() throws Exception {
        // Initialize the database
        skinRepository.saveAndFlush(skin);

        int databaseSizeBeforeUpdate = skinRepository.findAll().size();

        // Update the skin using partial update
        Skin partialUpdatedSkin = new Skin();
        partialUpdatedSkin.setId(skin.getId());

        partialUpdatedSkin.color(UPDATED_COLOR);

        restSkinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkin))
            )
            .andExpect(status().isOk());

        // Validate the Skin in the database
        List<Skin> skinList = skinRepository.findAll();
        assertThat(skinList).hasSize(databaseSizeBeforeUpdate);
        Skin testSkin = skinList.get(skinList.size() - 1);
        assertThat(testSkin.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    void patchNonExistingSkin() throws Exception {
        int databaseSizeBeforeUpdate = skinRepository.findAll().size();
        skin.setId(longCount.incrementAndGet());

        // Create the Skin
        SkinDTO skinDTO = skinMapper.toDto(skin);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, skinDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skin in the database
        List<Skin> skinList = skinRepository.findAll();
        assertThat(skinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSkin() throws Exception {
        int databaseSizeBeforeUpdate = skinRepository.findAll().size();
        skin.setId(longCount.incrementAndGet());

        // Create the Skin
        SkinDTO skinDTO = skinMapper.toDto(skin);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skin in the database
        List<Skin> skinList = skinRepository.findAll();
        assertThat(skinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSkin() throws Exception {
        int databaseSizeBeforeUpdate = skinRepository.findAll().size();
        skin.setId(longCount.incrementAndGet());

        // Create the Skin
        SkinDTO skinDTO = skinMapper.toDto(skin);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkinMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(skinDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Skin in the database
        List<Skin> skinList = skinRepository.findAll();
        assertThat(skinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSkin() throws Exception {
        // Initialize the database
        skinRepository.saveAndFlush(skin);

        int databaseSizeBeforeDelete = skinRepository.findAll().size();

        // Delete the skin
        restSkinMockMvc
            .perform(delete(ENTITY_API_URL_ID, skin.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Skin> skinList = skinRepository.findAll();
        assertThat(skinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
