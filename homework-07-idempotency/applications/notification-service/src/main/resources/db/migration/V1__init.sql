CREATE TABLE "public"."notification" (
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    email VARCHAR NOT NULL,
    order_id BIGINT,
    status VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT notification_pk PRIMARY KEY (id)
);

CREATE SEQUENCE notification_id_seq START WITH 1 INCREMENT BY 1;
