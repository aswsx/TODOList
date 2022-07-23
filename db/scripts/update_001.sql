CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR NOT NULL,
    password TEXT    NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     TIMESTAMP,
    done        BOOL    NOT NULL DEFAULT FALSE,
    user_id     INTEGER NOT NULL REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS categories
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE IF NOT EXISTS tasks_categories
(
    task_id     INTEGER NOT NULL REFERENCES tasks (id),
    categories_id INTEGER NOT NULL REFERENCES categories (id)
);
