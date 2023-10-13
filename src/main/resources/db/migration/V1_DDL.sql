CREATE TABLE user_record
  (
     id         UUID NOT NULL,
     first_name       VARCHAR(255),
     last_name       VARCHAR(255),
     username   VARCHAR(255),
     password VARCHAR(255),
     p1 VARCHAR(255),
     PRIMARY KEY (id)
  );