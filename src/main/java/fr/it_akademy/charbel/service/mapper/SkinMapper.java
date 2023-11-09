package fr.it_akademy.charbel.service.mapper;

import fr.it_akademy.charbel.domain.Skin;
import fr.it_akademy.charbel.service.dto.SkinDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Skin} and its DTO {@link SkinDTO}.
 */
@Mapper(componentModel = "spring")
public interface SkinMapper extends EntityMapper<SkinDTO, Skin> {}
