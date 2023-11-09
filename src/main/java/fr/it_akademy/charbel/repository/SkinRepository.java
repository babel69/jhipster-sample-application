package fr.it_akademy.charbel.repository;

import fr.it_akademy.charbel.domain.Skin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Skin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkinRepository extends JpaRepository<Skin, Long> {}
