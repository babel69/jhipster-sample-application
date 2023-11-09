package fr.it_akademy.charbel.service.impl;

import fr.it_akademy.charbel.domain.Power;
import fr.it_akademy.charbel.repository.PowerRepository;
import fr.it_akademy.charbel.service.PowerService;
import fr.it_akademy.charbel.service.dto.PowerDTO;
import fr.it_akademy.charbel.service.mapper.PowerMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.charbel.domain.Power}.
 */
@Service
@Transactional
public class PowerServiceImpl implements PowerService {

    private final Logger log = LoggerFactory.getLogger(PowerServiceImpl.class);

    private final PowerRepository powerRepository;

    private final PowerMapper powerMapper;

    public PowerServiceImpl(PowerRepository powerRepository, PowerMapper powerMapper) {
        this.powerRepository = powerRepository;
        this.powerMapper = powerMapper;
    }

    @Override
    public PowerDTO save(PowerDTO powerDTO) {
        log.debug("Request to save Power : {}", powerDTO);
        Power power = powerMapper.toEntity(powerDTO);
        power = powerRepository.save(power);
        return powerMapper.toDto(power);
    }

    @Override
    public PowerDTO update(PowerDTO powerDTO) {
        log.debug("Request to update Power : {}", powerDTO);
        Power power = powerMapper.toEntity(powerDTO);
        power = powerRepository.save(power);
        return powerMapper.toDto(power);
    }

    @Override
    public Optional<PowerDTO> partialUpdate(PowerDTO powerDTO) {
        log.debug("Request to partially update Power : {}", powerDTO);

        return powerRepository
            .findById(powerDTO.getId())
            .map(existingPower -> {
                powerMapper.partialUpdate(existingPower, powerDTO);

                return existingPower;
            })
            .map(powerRepository::save)
            .map(powerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PowerDTO> findAll() {
        log.debug("Request to get all Powers");
        return powerRepository.findAll().stream().map(powerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the powers where Character is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PowerDTO> findAllWhereCharacterIsNull() {
        log.debug("Request to get all powers where Character is null");
        return StreamSupport
            .stream(powerRepository.findAll().spliterator(), false)
            .filter(power -> power.getCharacter() == null)
            .map(powerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PowerDTO> findOne(Long id) {
        log.debug("Request to get Power : {}", id);
        return powerRepository.findById(id).map(powerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Power : {}", id);
        powerRepository.deleteById(id);
    }
}
