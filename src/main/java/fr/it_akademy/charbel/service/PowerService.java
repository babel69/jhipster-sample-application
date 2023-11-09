package fr.it_akademy.charbel.service;

import fr.it_akademy.charbel.service.dto.PowerDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.charbel.domain.Power}.
 */
public interface PowerService {
    /**
     * Save a power.
     *
     * @param powerDTO the entity to save.
     * @return the persisted entity.
     */
    PowerDTO save(PowerDTO powerDTO);

    /**
     * Updates a power.
     *
     * @param powerDTO the entity to update.
     * @return the persisted entity.
     */
    PowerDTO update(PowerDTO powerDTO);

    /**
     * Partially updates a power.
     *
     * @param powerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PowerDTO> partialUpdate(PowerDTO powerDTO);

    /**
     * Get all the powers.
     *
     * @return the list of entities.
     */
    List<PowerDTO> findAll();

    /**
     * Get all the PowerDTO where Character is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PowerDTO> findAllWhereCharacterIsNull();

    /**
     * Get the "id" power.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PowerDTO> findOne(Long id);

    /**
     * Delete the "id" power.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
