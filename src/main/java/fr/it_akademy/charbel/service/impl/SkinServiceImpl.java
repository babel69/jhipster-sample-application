package fr.it_akademy.charbel.service.impl;

import fr.it_akademy.charbel.domain.Skin;
import fr.it_akademy.charbel.repository.SkinRepository;
import fr.it_akademy.charbel.service.SkinService;
import fr.it_akademy.charbel.service.dto.SkinDTO;
import fr.it_akademy.charbel.service.mapper.SkinMapper;
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
 * Service Implementation for managing {@link fr.it_akademy.charbel.domain.Skin}.
 */
@Service
@Transactional
public class SkinServiceImpl implements SkinService {

    private final Logger log = LoggerFactory.getLogger(SkinServiceImpl.class);

    private final SkinRepository skinRepository;

    private final SkinMapper skinMapper;

    public SkinServiceImpl(SkinRepository skinRepository, SkinMapper skinMapper) {
        this.skinRepository = skinRepository;
        this.skinMapper = skinMapper;
    }

    @Override
    public SkinDTO save(SkinDTO skinDTO) {
        log.debug("Request to save Skin : {}", skinDTO);
        Skin skin = skinMapper.toEntity(skinDTO);
        skin = skinRepository.save(skin);
        return skinMapper.toDto(skin);
    }

    @Override
    public SkinDTO update(SkinDTO skinDTO) {
        log.debug("Request to update Skin : {}", skinDTO);
        Skin skin = skinMapper.toEntity(skinDTO);
        skin = skinRepository.save(skin);
        return skinMapper.toDto(skin);
    }

    @Override
    public Optional<SkinDTO> partialUpdate(SkinDTO skinDTO) {
        log.debug("Request to partially update Skin : {}", skinDTO);

        return skinRepository
            .findById(skinDTO.getId())
            .map(existingSkin -> {
                skinMapper.partialUpdate(existingSkin, skinDTO);

                return existingSkin;
            })
            .map(skinRepository::save)
            .map(skinMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkinDTO> findAll() {
        log.debug("Request to get all Skins");
        return skinRepository.findAll().stream().map(skinMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the skins where Character is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SkinDTO> findAllWhereCharacterIsNull() {
        log.debug("Request to get all skins where Character is null");
        return StreamSupport
            .stream(skinRepository.findAll().spliterator(), false)
            .filter(skin -> skin.getCharacter() == null)
            .map(skinMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SkinDTO> findOne(Long id) {
        log.debug("Request to get Skin : {}", id);
        return skinRepository.findById(id).map(skinMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Skin : {}", id);
        skinRepository.deleteById(id);
    }
}
