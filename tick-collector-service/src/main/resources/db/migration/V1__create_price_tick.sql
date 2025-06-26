CREATE TABLE price_tick (
                            tradeAt        TIMESTAMPTZ NOT NULL,
                            symbol      TEXT        NOT NULL,
                            price       DOUBLE PRECISION NOT NULL,
                            volume      BIGINT      NOT NULL
                          , created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
                          , updated_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

SELECT create_hypertable('price_tick', 'tradeAt', migrate_data => true);
CREATE INDEX ON price_tick (symbol, tradeAt DESC);

-- Function to update updated_at on row modification
CREATE OR REPLACE FUNCTION update_price_tick_updated_at()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = now();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger to invoke function before any UPDATE
CREATE TRIGGER trg_price_tick_updated_at
BEFORE UPDATE ON price_tick
FOR EACH ROW
EXECUTE PROCEDURE update_price_tick_updated_at();