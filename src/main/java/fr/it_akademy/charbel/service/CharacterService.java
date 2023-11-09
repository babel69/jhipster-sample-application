package fr.it_akademy.charbel.service;

import fr.it_akademy.charbel.service.dto.CharacterDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.charbel.domain.Character}.
 */
public interface CharacterService {
    /**
     * Save a character.
     *
     * @param characterDTO the entity to save.
     * @return the persisted entity.
     */
    CharacterDTO save(CharacterDTO characterDTO);

    /**
     * Updates a character.
     *
     * @param characterDTO the entity to update.
     * @return the persisted entity.
     */
    CharacterDTO update(CharacterDTO characterDTO);

    /**
     * Partially updates a character.
     *
     * @param characterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CharacterDTO> partialUpdate(CharacterDTO characterDTO);

    /**
     * Get all the characters.
     *
     * @return the list of entities.
     */
    List<CharacterDTO> findAll();

    /**
     * Get the "id" character.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CharacterDTO> findOne(Long id);

    /**
     * Delete the "id" character.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
