package fr.it_akademy.charbel.web.rest;

import fr.it_akademy.charbel.repository.CharacterRepository;
import fr.it_akademy.charbel.service.CharacterService;
import fr.it_akademy.charbel.service.dto.CharacterDTO;
import fr.it_akademy.charbel.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.it_akademy.charbel.domain.Character}.
 */
@RestController
@RequestMapping("/api/characters")
public class CharacterResource {

    private final Logger log = LoggerFactory.getLogger(CharacterResource.class);

    private static final String ENTITY_NAME = "character";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CharacterService characterService;

    private final CharacterRepository characterRepository;

    public CharacterResource(CharacterService characterService, CharacterRepository characterRepository) {
        this.characterService = characterService;
        this.characterRepository = characterRepository;
    }

    /**
     * {@code POST  /characters} : Create a new character.
     *
     * @param characterDTO the characterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new characterDTO, or with status {@code 400 (Bad Request)} if the character has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CharacterDTO> createCharacter(@RequestBody CharacterDTO characterDTO) throws URISyntaxException {
        log.debug("REST request to save Character : {}", characterDTO);
        if (characterDTO.getId() != null) {
            throw new BadRequestAlertException("A new character cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CharacterDTO result = characterService.save(characterDTO);
        return ResponseEntity
            .created(new URI("/api/characters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /characters/:id} : Updates an existing character.
     *
     * @param id the id of the characterDTO to save.
     * @param characterDTO the characterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated characterDTO,
     * or with status {@code 400 (Bad Request)} if the characterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the characterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CharacterDTO> updateCharacter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CharacterDTO characterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Character : {}, {}", id, characterDTO);
        if (characterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, characterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!characterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CharacterDTO result = characterService.update(characterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, characterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /characters/:id} : Partial updates given fields of an existing character, field will ignore if it is null
     *
     * @param id the id of the characterDTO to save.
     * @param characterDTO the characterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated characterDTO,
     * or with status {@code 400 (Bad Request)} if the characterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the characterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the characterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CharacterDTO> partialUpdateCharacter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CharacterDTO characterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Character partially : {}, {}", id, characterDTO);
        if (characterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, characterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!characterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CharacterDTO> result = characterService.partialUpdate(characterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, characterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /characters} : get all the characters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of characters in body.
     */
    @GetMapping("")
    public List<CharacterDTO> getAllCharacters() {
        log.debug("REST request to get all Characters");
        return characterService.findAll();
    }

    /**
     * {@code GET  /characters/:id} : get the "id" character.
     *
     * @param id the id of the characterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the characterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable Long id) {
        log.debug("REST request to get Character : {}", id);
        Optional<CharacterDTO> characterDTO = characterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(characterDTO);
    }

    /**
     * {@code DELETE  /characters/:id} : delete the "id" character.
     *
     * @param id the id of the characterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        log.debug("REST request to delete Character : {}", id);
        characterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
