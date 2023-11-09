package fr.it_akademy.charbel.service.mapper;

import fr.it_akademy.charbel.domain.Job;
import fr.it_akademy.charbel.service.dto.JobDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Job} and its DTO {@link JobDTO}.
 */
@Mapper(componentModel = "spring")
public interface JobMapper extends EntityMapper<JobDTO, Job> {}
