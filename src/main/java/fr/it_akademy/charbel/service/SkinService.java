package fr.it_akademy.charbel.service;

import fr.it_akademy.charbel.service.dto.SkinDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.charbel.domain.Skin}.
 */
public interface SkinService {
    /**
     * Save a skin.
     *
     * @param skinDTO the entity to save.
     * @return the persisted entity.
     */
    SkinDTO save(SkinDTO skinDTO);

    /**
     * Updates a skin.
     *
     * @param skinDTO the entity to update.
     * @return the persisted entity.
     */
    SkinDTO update(SkinDTO skinDTO);

    /**
     * Partially updates a skin.
     *
     * @param skinDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SkinDTO> partialUpdate(SkinDTO skinDTO);

    /**
     * Get all the skins.
     *
     * @return the list of entities.
     */
    List<SkinDTO> findAll();

    /**
     * Get all the SkinDTO where Character is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<SkinDTO> findAllWhereCharacterIsNull();

    /**
     * Get the "id" skin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SkinDTO> findOne(Long id);

    /**
     * Delete the "id" skin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
