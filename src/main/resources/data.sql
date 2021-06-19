DROP TABLE IF EXISTS doors;
DROP TABLE IF EXISTS users;

CREATE TABLE doors (
  id INTEGER AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE users (
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  room_id INTEGER,
  foreign key (room_id) references doors(id)
);