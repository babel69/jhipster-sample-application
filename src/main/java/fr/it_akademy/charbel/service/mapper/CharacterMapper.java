package fr.it_akademy.charbel.service.mapper;

import fr.it_akademy.charbel.domain.Character;
import fr.it_akademy.charbel.domain.Country;
import fr.it_akademy.charbel.domain.Job;
import fr.it_akademy.charbel.domain.Power;
import fr.it_akademy.charbel.domain.Skin;
import fr.it_akademy.charbel.service.dto.CharacterDTO;
import fr.it_akademy.charbel.service.dto.CountryDTO;
import fr.it_akademy.charbel.service.dto.JobDTO;
import fr.it_akademy.charbel.service.dto.PowerDTO;
import fr.it_akademy.charbel.service.dto.SkinDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Character} and its DTO {@link CharacterDTO}.
 */
@Mapper(componentModel = "spring")
public interface CharacterMapper extends EntityMapper<CharacterDTO, Character> {
    @Mapping(target = "job", source = "job", qualifiedByName = "jobId")
    @Mapping(target = "country", source = "country", qualifiedByName = "countryId")
    @Mapping(target = "power", source = "power", qualifiedByName = "powerId")
    @Mapping(target = "skin", source = "skin", qualifiedByName = "skinId")
    CharacterDTO toDto(Character s);

    @Named("jobId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JobDTO toDtoJobId(Job job);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);

    @Named("powerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PowerDTO toDtoPowerId(Power power);

    @Named("skinId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SkinDTO toDtoSkinId(Skin skin);
}
