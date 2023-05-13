CREATE TABLE user_notifications (
                                    id BIGSERIAL NOT NULL PRIMARY KEY,
                                    username VARCHAR(255) NOT NULL,
                                    registered_price DECIMAL(18, 8) NOT NULL,
                                    crypto_currency_id VARCHAR(255) NOT NULL,
                                    FOREIGN KEY (crypto_currency_id) REFERENCES crypto_currencies(id) ON DELETE CASCADE
);