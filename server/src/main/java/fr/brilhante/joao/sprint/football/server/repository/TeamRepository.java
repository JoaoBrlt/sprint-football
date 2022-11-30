package fr.brilhante.joao.sprint.football.server.repository;

import fr.brilhante.joao.sprint.football.server.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository to store and retrieve football teams.
 */
public interface TeamRepository extends JpaRepository<Team, UUID> {

	/**
	 * Finds a football team by acronym (case-insensitive).
	 *
	 * @param acronym the football team acronym to find
	 * @return the football team with the provided acronym, if found
	 */
	Optional<Team> findByAcronymIgnoreCase(String acronym);
}
