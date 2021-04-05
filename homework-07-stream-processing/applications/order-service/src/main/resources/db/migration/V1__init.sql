CREATE TABLE orders (
    id BIGINT NOT NULL,
    price DECIMAL NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT order_pk PRIMARY KEY (id)
);
