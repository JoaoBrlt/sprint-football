package fr.brilhante.joao.sprint.football.server.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Model of a football team.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private UUID id;

	@Column(nullable = false, length = 50)
	@NonNull
	private String name;

	@Column(nullable = false, unique = true, length = 3)
	@NonNull
	private String acronym;

	@Column(nullable = false, precision = 14, scale = 2)
	@NonNull
	private BigDecimal budget;

	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private List<Player> players = new ArrayList<>();

	/**
	 * Adds a football player to the team.
	 * <p>
	 * This method sets the team of the player so that the association is properly registered in the database.
	 * </p>
	 *
	 * @param player the player to add
	 */
	public void addPlayer(Player player) {
		players.add(player);
		player.setTeam(this);
	}

	/**
	 * Removes a football player from the team.
	 * <p>
	 * This method resets the team of the player so that the association is properly registered in the database.
	 * </p>
	 *
	 * @param player the player to remove
	 */
	public void removePlayer(Player player) {
		players.remove(player);
		player.setTeam(null);
	}
}
