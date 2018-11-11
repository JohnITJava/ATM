CREATE TABLE IF NOT EXISTS humans (
  id       INTEGER PRIMARY KEY AUTOINCREMENT
    UNIQUE
                   NOT NULL,
  role     TEXT    NOT NULL,
  username TEXT    UNIQUE
                   NOT NULL,
  password TEXT    NOT NULL,
  balance  DOUBLE
);

CREATE TABLE IF NOT EXISTS operations (
  id        INTEGER PRIMARY KEY AUTOINCREMENT
                 NOT NULL
    UNIQUE,
  operation TEXT NOT NULL,
  id_hum    INTEGER REFERENCES humans (id)
);


INSERT INTO humans (role, username, password, balance)
VALUES ('User', 'user1', 'user1', 0);

INSERT INTO humans (role, username, password, balance)
VALUES ('User', 'user2', 'user2', 500);

INSERT INTO humans (role, username, password)
VALUES ('Admin', 'admin1', 'admin1');

