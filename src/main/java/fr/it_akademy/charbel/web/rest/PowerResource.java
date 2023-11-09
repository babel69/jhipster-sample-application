package fr.it_akademy.charbel.web.rest;

import fr.it_akademy.charbel.repository.PowerRepository;
import fr.it_akademy.charbel.service.PowerService;
import fr.it_akademy.charbel.service.dto.PowerDTO;
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
 * REST controller for managing {@link fr.it_akademy.charbel.domain.Power}.
 */
@RestController
@RequestMapping("/api/powers")
public class PowerResource {

    private final Logger log = LoggerFactory.getLogger(PowerResource.class);

    private static final String ENTITY_NAME = "power";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PowerService powerService;

    private final PowerRepository powerRepository;

    public PowerResource(PowerService powerService, PowerRepository powerRepository) {
        this.powerService = powerService;
        this.powerRepository = powerRepository;
    }

    /**
     * {@code POST  /powers} : Create a new power.
     *
     * @param powerDTO the powerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new powerDTO, or with status {@code 400 (Bad Request)} if the power has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PowerDTO> createPower(@RequestBody PowerDTO powerDTO) throws URISyntaxException {
        log.debug("REST request to save Power : {}", powerDTO);
        if (powerDTO.getId() != null) {
            throw new BadRequestAlertException("A new power cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PowerDTO result = powerService.save(powerDTO);
        return ResponseEntity
            .created(new URI("/api/powers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /powers/:id} : Updates an existing power.
     *
     * @param id the id of the powerDTO to save.
     * @param powerDTO the powerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated powerDTO,
     * or with status {@code 400 (Bad Request)} if the powerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the powerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PowerDTO> updatePower(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PowerDTO powerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Power : {}, {}", id, powerDTO);
        if (powerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, powerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!powerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PowerDTO result = powerService.update(powerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, powerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /powers/:id} : Partial updates given fields of an existing power, field will ignore if it is null
     *
     * @param id the id of the powerDTO to save.
     * @param powerDTO the powerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated powerDTO,
     * or with status {@code 400 (Bad Request)} if the powerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the powerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the powerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PowerDTO> partialUpdatePower(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PowerDTO powerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Power partially : {}, {}", id, powerDTO);
        if (powerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, powerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!powerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PowerDTO> result = powerService.partialUpdate(powerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, powerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /powers} : get all the powers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of powers in body.
     */
    @GetMapping("")
    public List<PowerDTO> getAllPowers(@RequestParam(required = false) String filter) {
        if ("character-is-null".equals(filter)) {
            log.debug("REST request to get all Powers where character is null");
            return powerService.findAllWhereCharacterIsNull();
        }
        log.debug("REST request to get all Powers");
        return powerService.findAll();
    }

    /**
     * {@code GET  /powers/:id} : get the "id" power.
     *
     * @param id the id of the powerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the powerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PowerDTO> getPower(@PathVariable Long id) {
        log.debug("REST request to get Power : {}", id);
        Optional<PowerDTO> powerDTO = powerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(powerDTO);
    }

    /**
     * {@code DELETE  /powers/:id} : delete the "id" power.
     *
     * @param id the id of the powerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePower(@PathVariable Long id) {
        log.debug("REST request to delete Power : {}", id);
        powerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
