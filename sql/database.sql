CREATE DATABASE IF NOT EXISTS cloudmanufacturing DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

CREATE USER IF NOT EXISTS 'dbuser'@'localhost' IDENTIFIED BY '123456';

GRANT ALL ON cloudmanufacturing.* TO 'dbuser'@'localhost';