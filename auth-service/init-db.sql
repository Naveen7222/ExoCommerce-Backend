-- Create auth_db database if it doesn't exist
CREATE DATABASE IF NOT EXISTS auth_db;

-- Use the database
USE auth_db;

-- The auth_users table will be created automatically by Spring Boot JPA
-- with ddl-auto: update setting in application.yml

-- Verify database creation
SELECT 'auth_db database created successfully' AS status;
