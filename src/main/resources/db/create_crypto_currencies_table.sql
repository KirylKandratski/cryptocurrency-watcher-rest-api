CREATE TABLE crypto_currencies (
                                   id VARCHAR(255) NOT NULL PRIMARY KEY,
                                   symbol VARCHAR(255) NOT NULL UNIQUE,
                                   current_price DECIMAL(18, 8) NOT NULL
);
