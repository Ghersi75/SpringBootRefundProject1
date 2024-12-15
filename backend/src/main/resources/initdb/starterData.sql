CREATE TYPE USER_ROLE AS ENUM ('EMPLOYEE', 'MANAGER');
CREATE TYPE TICKET_STATUS AS ENUM ('PENDING', 'APPROVED', 'DENIED');

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  user_role USER_ROLE NOT NULL DEFAULT 'EMPLOYEE'
);

CREATE TABLE tickets (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES users (id),
  amount INT NOT NULL,
  ticket_status TICKET_STATUS NOT NULL DEFAULT 'PENDING',
  -- https://www.postgresql.org/docs/current/datatype-datetime.html
  -- Timestamp keeps track of date and time of day which will be useful when sorting for managers to see oldest first
  time_added TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO users (username, email, password_hash, first_name, last_name, user_role)
      VALUES ('Test User', 'test@test.com', '$2a$12$3cvg9nU3gnskUkpX3ClOTuLhRBXTyPwc2HeBHbgGzxOjGKspoRLn6', 'User first', 'user last', 'MANAGER');
-- Password123$$$ 12 salt hashed with bcrypt ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

INSERT INTO tickets (user_id, amount)
  VALUES (1, 1000);