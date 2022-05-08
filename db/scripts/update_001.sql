CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR NOT NULL,
    password TEXT    NOT NULL
);

CREATE TABLE IF NOT EXISTS todo
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     TIMESTAMP,
    done        BOOL    NOT NULL DEFAULT FALSE,
    user_id     INTEGER NOT NULL REFERENCES users (id)
);