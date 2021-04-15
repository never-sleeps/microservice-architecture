CREATE TABLE "public"."billing_account" (
    id BIGINT NOT NULL,
    email VARCHAR NOT NULL,
    user_id BIGINT NOT NULL,
    balance NUMERIC DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT billing_account_pk PRIMARY KEY (id)
);

CREATE SEQUENCE billing_account_id_seq START WITH 1 INCREMENT BY 1;
