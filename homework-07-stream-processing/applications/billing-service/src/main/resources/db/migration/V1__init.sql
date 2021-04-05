CREATE TABLE ACCOUNTS (
    id BIGINT NOT NULL,
    mail VARCHAR NOT NULL,
    user_id BIGINT NOT NULL,
    balance NUMERIC DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT billing_account_pk PRIMARY KEY (id)
);
