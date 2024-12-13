CREATE TYPE Role AS ENUM ('EMPLOYEE', 'MANAGER');

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  role Role NOT NULL DEFAULT 'EMPLOYEE'
);

INSERT INTO users (username, email, password_hash, first_name, last_name)
  VALUES ('User1', 'user1@gmail.com', '22222', 'User first', 'user last');