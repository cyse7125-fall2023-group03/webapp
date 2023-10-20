-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE test_table (test_id INT, test_column VARCHAR(80),PRIMARY KEY (test_id));
-- changeset liquibase:2
CREATE TABLE RandomTable (
    ID INT,
    Name VARCHAR(50),
    Age INT,
    Email VARCHAR(50) DEFAULT 'email@example.com'
);