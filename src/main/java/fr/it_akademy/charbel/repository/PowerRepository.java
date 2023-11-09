package fr.it_akademy.charbel.repository;

import fr.it_akademy.charbel.domain.Power;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Power entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PowerRepository extends JpaRepository<Power, Long> {}
