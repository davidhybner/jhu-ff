# DB migration file for MySQL. Run the following command to build the database:
# mysql -u root < (absolute-path)/jhu-ff/db.sql

DROP DATABASE IF EXISTS jhu_ff;
CREATE DATABASE jhu_ff;
USE jhu_ff;

CREATE TABLE users (
  username varchar(50) NOT NULL,
  password varchar(50) NOT NULL,
  PRIMARY KEY (username)
);

CREATE TABLE user_roles (
  username varchar(50) NOT NULL,
  role varchar(50) NOT NULL
);

CREATE TABLE leagues (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  owner varchar(50) DEFAULT NULL,
  public_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM;

CREATE TABLE league_players (
  league_id int(11) NOT NULL,
  username varchar(50) NOT NULL,
  offense_team int(11),
  defense_team int(11),
  score int(11),
  PRIMARY KEY (league_id,username)
);

CREATE TABLE season (
  year int(4) NOT NULL,
  week int(1) NOT NULL,
  PRIMARY KEY (year, week)
);

CREATE TABLE real_teams (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  logo varchar(80),
  primary_color varchar(10),
  secondary_color varchar(10),
  PRIMARY KEY  (id)
) ENGINE=MyISAM;

INSERT INTO users VALUES
  ("user", "password"),
  ("player", "password"),
  ("admin", "password");

INSERT INTO user_roles VALUES
  ("user", "player"),
  ("player", "player"),
  ("admin", "admin");

INSERT INTO real_teams VALUES
  (1, "San Francisco 49ers", "logo_url", "0xE60000", "0x996600"),
  (2, "Baltimore Ravens", "logo_url", "0x8A008A", "0x000000"),
  (3, "Green Bay Packers", "logo_url", "0x009900", "0xFFFF00"),
  (4, "New England Patriots", "logo_url", "0x0000CC", "0x9494b8"),
  (5, "Chicago Bears", "logo_url", "0xFF6600", "0x00006B"),
  (6, "Houston Texans", "logo_url", "0x000066", "0xFF0000"),
  (7, "Atlanta Falcons", "logo_url", "0x000000", "0xFF0000"),
  (8, "Denver Broncos", "logo_url", "0xFF7519", "0x3333FF");

INSERT INTO season VALUES
  (2012, 1);

INSERT INTO leagues VALUES
  (1, "Some League Name", "user", "abc123");

INSERT INTO league_players VALUES
  (1, "user", 1, 1, 0),
  (1, "player", 0, 0, 0);