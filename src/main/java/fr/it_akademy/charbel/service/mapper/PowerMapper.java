package fr.it_akademy.charbel.service.mapper;

import fr.it_akademy.charbel.domain.Power;
import fr.it_akademy.charbel.service.dto.PowerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Power} and its DTO {@link PowerDTO}.
 */
@Mapper(componentModel = "spring")
public interface PowerMapper extends EntityMapper<PowerDTO, Power> {}
