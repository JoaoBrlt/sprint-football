package fr.brilhante.joao.sprint.football.server.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * Model of a football player.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private UUID id;

	@Column(nullable = false, length = 50)
	@NonNull
	private String firstName;

	@Column(nullable = false, length = 50)
	@NonNull
	private String lastName;

	@Column(nullable = false)
	@NonNull
	private Integer position;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(nullable = false)
	@ToString.Exclude
	private Team team;
}
