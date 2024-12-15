CREATE TYPE USER_ROLE AS ENUM ('EMPLOYEE', 'MANAGER');

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  user_role USER_ROLE NOT NULL DEFAULT 'EMPLOYEE'
);

INSERT INTO users (username, email, password_hash, first_name, last_name, user_role)
      VALUES ('Test User', 'test@test.com', '$2a$12$3cvg9nU3gnskUkpX3ClOTuLhRBXTyPwc2HeBHbgGzxOjGKspoRLn6', 'User first', 'user last', 'MANAGER');
-- Password123$$$ 12 salt hashed with bcrypt ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
