CREATE TABLE player
(
    id         UUID        NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    position   INTEGER     NOT NULL,
    team_id    UUID        NOT NULL,
    CONSTRAINT pk_player PRIMARY KEY (id)
);

CREATE TABLE team
(
    id      UUID           NOT NULL,
    name    VARCHAR(50)    NOT NULL,
    acronym VARCHAR(3)     NOT NULL,
    budget  DECIMAL(14, 2) NOT NULL,
    CONSTRAINT pk_team PRIMARY KEY (id)
);

ALTER TABLE team
    ADD CONSTRAINT uc_team_acronym UNIQUE (acronym);

ALTER TABLE player
    ADD CONSTRAINT FK_PLAYER_ON_TEAM FOREIGN KEY (team_id) REFERENCES team (id);
