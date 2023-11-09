package fr.it_akademy.charbel.web.rest;

import fr.it_akademy.charbel.repository.SkinRepository;
import fr.it_akademy.charbel.service.SkinService;
import fr.it_akademy.charbel.service.dto.SkinDTO;
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
 * REST controller for managing {@link fr.it_akademy.charbel.domain.Skin}.
 */
@RestController
@RequestMapping("/api/skins")
public class SkinResource {

    private final Logger log = LoggerFactory.getLogger(SkinResource.class);

    private static final String ENTITY_NAME = "skin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkinService skinService;

    private final SkinRepository skinRepository;

    public SkinResource(SkinService skinService, SkinRepository skinRepository) {
        this.skinService = skinService;
        this.skinRepository = skinRepository;
    }

    /**
     * {@code POST  /skins} : Create a new skin.
     *
     * @param skinDTO the skinDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skinDTO, or with status {@code 400 (Bad Request)} if the skin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SkinDTO> createSkin(@RequestBody SkinDTO skinDTO) throws URISyntaxException {
        log.debug("REST request to save Skin : {}", skinDTO);
        if (skinDTO.getId() != null) {
            throw new BadRequestAlertException("A new skin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkinDTO result = skinService.save(skinDTO);
        return ResponseEntity
            .created(new URI("/api/skins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skins/:id} : Updates an existing skin.
     *
     * @param id the id of the skinDTO to save.
     * @param skinDTO the skinDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skinDTO,
     * or with status {@code 400 (Bad Request)} if the skinDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SkinDTO> updateSkin(@PathVariable(value = "id", required = false) final Long id, @RequestBody SkinDTO skinDTO)
        throws URISyntaxException {
        log.debug("REST request to update Skin : {}, {}", id, skinDTO);
        if (skinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skinDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SkinDTO result = skinService.update(skinDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skinDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /skins/:id} : Partial updates given fields of an existing skin, field will ignore if it is null
     *
     * @param id the id of the skinDTO to save.
     * @param skinDTO the skinDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skinDTO,
     * or with status {@code 400 (Bad Request)} if the skinDTO is not valid,
     * or with status {@code 404 (Not Found)} if the skinDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the skinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SkinDTO> partialUpdateSkin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SkinDTO skinDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Skin partially : {}, {}", id, skinDTO);
        if (skinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skinDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SkinDTO> result = skinService.partialUpdate(skinDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skinDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /skins} : get all the skins.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skins in body.
     */
    @GetMapping("")
    public List<SkinDTO> getAllSkins(@RequestParam(required = false) String filter) {
        if ("character-is-null".equals(filter)) {
            log.debug("REST request to get all Skins where character is null");
            return skinService.findAllWhereCharacterIsNull();
        }
        log.debug("REST request to get all Skins");
        return skinService.findAll();
    }

    /**
     * {@code GET  /skins/:id} : get the "id" skin.
     *
     * @param id the id of the skinDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skinDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SkinDTO> getSkin(@PathVariable Long id) {
        log.debug("REST request to get Skin : {}", id);
        Optional<SkinDTO> skinDTO = skinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skinDTO);
    }

    /**
     * {@code DELETE  /skins/:id} : delete the "id" skin.
     *
     * @param id the id of the skinDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkin(@PathVariable Long id) {
        log.debug("REST request to delete Skin : {}", id);
        skinService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
