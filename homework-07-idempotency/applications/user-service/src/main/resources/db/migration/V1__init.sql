CREATE TABLE "public"."user" (
     id INTEGER NOT NULL,
     username VARCHAR NOT NULL,
     first_name VARCHAR NOT NULL,
     last_name VARCHAR NOT NULL,
     email VARCHAR NOT NULL,
     phone VARCHAR NOT NULL,
     CONSTRAINT user_pk PRIMARY KEY (id)
);

CREATE SEQUENCE user_id_seq START WITH 1 INCREMENT BY 1;