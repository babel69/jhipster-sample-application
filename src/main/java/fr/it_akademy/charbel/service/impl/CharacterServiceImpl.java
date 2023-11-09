package fr.it_akademy.charbel.service.impl;

import fr.it_akademy.charbel.domain.Character;
import fr.it_akademy.charbel.repository.CharacterRepository;
import fr.it_akademy.charbel.service.CharacterService;
import fr.it_akademy.charbel.service.dto.CharacterDTO;
import fr.it_akademy.charbel.service.mapper.CharacterMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.charbel.domain.Character}.
 */
@Service
@Transactional
public class CharacterServiceImpl implements CharacterService {

    private final Logger log = LoggerFactory.getLogger(CharacterServiceImpl.class);

    private final CharacterRepository characterRepository;

    private final CharacterMapper characterMapper;

    public CharacterServiceImpl(CharacterRepository characterRepository, CharacterMapper characterMapper) {
        this.characterRepository = characterRepository;
        this.characterMapper = characterMapper;
    }

    @Override
    public CharacterDTO save(CharacterDTO characterDTO) {
        log.debug("Request to save Character : {}", characterDTO);
        Character character = characterMapper.toEntity(characterDTO);
        character = characterRepository.save(character);
        return characterMapper.toDto(character);
    }

    @Override
    public CharacterDTO update(CharacterDTO characterDTO) {
        log.debug("Request to update Character : {}", characterDTO);
        Character character = characterMapper.toEntity(characterDTO);
        character = characterRepository.save(character);
        return characterMapper.toDto(character);
    }

    @Override
    public Optional<CharacterDTO> partialUpdate(CharacterDTO characterDTO) {
        log.debug("Request to partially update Character : {}", characterDTO);

        return characterRepository
            .findById(characterDTO.getId())
            .map(existingCharacter -> {
                characterMapper.partialUpdate(existingCharacter, characterDTO);

                return existingCharacter;
            })
            .map(characterRepository::save)
            .map(characterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterDTO> findAll() {
        log.debug("Request to get all Characters");
        return characterRepository.findAll().stream().map(characterMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CharacterDTO> findOne(Long id) {
        log.debug("Request to get Character : {}", id);
        return characterRepository.findById(id).map(characterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Character : {}", id);
        characterRepository.deleteById(id);
    }
}
