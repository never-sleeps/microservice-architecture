CREATE TABLE "public"."order" (
    id BIGINT NOT NULL,
    price DECIMAL NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    status VARCHAR NOT NULL,
    CONSTRAINT order_pk PRIMARY KEY (id)
);

CREATE SEQUENCE order_id_seq START WITH 1 INCREMENT BY 1;
