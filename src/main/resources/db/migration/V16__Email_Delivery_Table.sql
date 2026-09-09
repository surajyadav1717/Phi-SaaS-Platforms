CREATE TABLE email_delivery (
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    recipient VARCHAR(255) NOT NULL,

    subject VARCHAR(255) NOT NULL,

    message TEXT NOT NULL,

    status VARCHAR(20) NOT NULL,

    attempts INT NOT NULL DEFAULT 0,

    error_message TEXT,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    sent_at TIMESTAMP NULL
);