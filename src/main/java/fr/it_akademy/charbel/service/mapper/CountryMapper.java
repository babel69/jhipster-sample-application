package fr.it_akademy.charbel.service.mapper;

import fr.it_akademy.charbel.domain.Country;
import fr.it_akademy.charbel.service.dto.CountryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {}
