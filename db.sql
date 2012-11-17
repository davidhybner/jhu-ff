# DB migration file for MySQL. Run the following command to build the database:
# mysql -u root < (absolute-path)/jhu-ff/db.sql

DROP DATABASE IF EXISTS jhu_ff_test;
CREATE DATABASE jhu_ff_test;
USE jhu_ff_test;

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
  PRIMARY KEY (league_id,username)
);

INSERT INTO users VALUES
  ("user", "password"),
  ("admin", "password");

INSERT INTO user_roles VALUES
  ("user", "player"),
  ("admin", "admin");


  
  