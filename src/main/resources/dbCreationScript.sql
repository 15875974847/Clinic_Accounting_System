DROP SCHEMA IF EXISTS clinic;
CREATE SCHEMA clinic;


-- after go to mysql cmd and type ... to create datatbase user with all priveleges
CREATE USER 'ClinicAdmin'@'localhost' IDENTIFIED BY 'pass';
GRANT ALL PRIVILEGES ON `clinic`.* TO 'ClinicAdmin'@'localhost';
FLUSH PRIVILEGES;