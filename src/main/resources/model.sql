CREATE DATABASE IF NOT EXISTS sharding;

DROP TABLE IF EXISTS sharding.payment;
CREATE TABLE  sharding.payment (
  id varchar(100)  NOT NULL, -- uuid кривой надо postrgres
  sender varchar(100) NOT NULL,
  receiver varchar(100) NOT NULL,
  amount bigint NOT NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;