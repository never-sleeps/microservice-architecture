CREATE TABLE NOTIFICATIONS (
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    mail VARCHAR NOT NULL,
    order_id BIGINT,
    status VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT notification_pk PRIMARY KEY (id)
);
