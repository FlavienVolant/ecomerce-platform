CREATE TABLE articles (
    id UUID PRIMARY KEY,
    name varchar(255) NOT NULL,
    price_in_cents int NOT NULL CHECK (price_in_cents >= 0)
);

CREATE TYPE command_status as ENUM ('pending', 'confirmed', 'cancelled');

CREATE TABLE commands (
    id UUID PRIMARY KEY,
    status command_status NOT NULL
);

CREATE TABLE articles_in_commands(
    product_id UUID REFERENCES articles(id),
    command_id UUID REFERENCES commands(id),
    quantity int NOT NULL CHECK (quantity > 0),
    unit_price_in_cents int NOT NULL CHECK (unit_price_in_cents >= 0),
    PRIMARY KEY(command_id, product_id)
);