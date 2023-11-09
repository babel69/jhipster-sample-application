package fr.it_akademy.charbel.service;

import fr.it_akademy.charbel.service.dto.JobDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.charbel.domain.Job}.
 */
public interface JobService {
    /**
     * Save a job.
     *
     * @param jobDTO the entity to save.
     * @return the persisted entity.
     */
    JobDTO save(JobDTO jobDTO);

    /**
     * Updates a job.
     *
     * @param jobDTO the entity to update.
     * @return the persisted entity.
     */
    JobDTO update(JobDTO jobDTO);

    /**
     * Partially updates a job.
     *
     * @param jobDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JobDTO> partialUpdate(JobDTO jobDTO);

    /**
     * Get all the jobs.
     *
     * @return the list of entities.
     */
    List<JobDTO> findAll();

    /**
     * Get all the JobDTO where Character is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<JobDTO> findAllWhereCharacterIsNull();

    /**
     * Get the "id" job.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobDTO> findOne(Long id);

    /**
     * Delete the "id" job.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
