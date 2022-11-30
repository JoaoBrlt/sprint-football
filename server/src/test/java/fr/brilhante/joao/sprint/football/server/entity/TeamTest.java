package fr.brilhante.joao.sprint.football.server.entity;

import fr.brilhante.joao.sprint.football.server.model.Player;
import fr.brilhante.joao.sprint.football.server.model.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TeamTest {

	@Test
	@DisplayName("Should set the football team when a player is added")
	void addPlayer() {
		Team team = new Team("Real Madrid", "RMD", new BigDecimal("839000000"));
		Player player = new Player("Karim", "Benzema", 9);

		team.addPlayer(player);

		assertThat(team.getPlayers()).hasSize(1);
		assertThat(player.getTeam()).isEqualTo(team);
	}

	@Test
	@DisplayName("Should reset the football team when a player is removed")
	void removePlayer() {
		Team team = new Team("Bayern Munich", "MUN", new BigDecimal("920700000"));
		Player player = new Player("Manuel", "Neuer", 1);

		team.addPlayer(player);
		team.removePlayer(player);

		assertThat(team.getPlayers()).isEmpty();
		assertThat(player.getTeam()).isNull();
	}
}
