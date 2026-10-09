CREATE TABLE transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    reference VARCHAR(50) NOT NULL UNIQUE,
    type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    amount NUMERIC(19, 4) NOT NULL,
    currency VARCHAR(3) NOT NULL,

    source_wallet_id UUID,
    destination_wallet_id UUID,

    idempotency_key VARCHAR(100) UNIQUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints
    CONSTRAINT fk_transactions_source_wallet
        FOREIGN KEY (source_wallet_id)
        REFERENCES wallets(id),

    CONSTRAINT fk_transactions_destination_wallet
        FOREIGN KEY (destination_wallet_id)
        REFERENCES wallets(id),

    -- Check constraints
    CONSTRAINT chk_transactions_type
        CHECK (type IN ('DEPOSIT', 'WITHDRAW', 'TRANSFER')),
    CONSTRAINT chk_transactions_status
        CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED')),
    CONSTRAINT chk_transactions_amount
        CHECK (amount > 0),
    CONSTRAINT chk_transactions_currency
        CHECK (length(currency) = 3)
);

-- Indexes for performance optimization
CREATE INDEX idx_transactions_reference
    ON transactions(reference);

CREATE INDEX idx_transactions_source_wallet
    ON transactions(source_wallet_id);

CREATE INDEX idx_transactions_destination_wallet
    ON transactions(destination_wallet_id);

CREATE INDEX idx_transactions_idempotency_key
    ON transactions(idempotency_key);