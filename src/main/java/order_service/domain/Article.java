package order_service.domain;

import java.util.UUID;

import order_service.exceptions.PriceLowerThanZeroException;

public class Article {
    private UUID id;
    private String name;
    private int priceInCents;

    public Article(String name, int priceInCents) throws PriceLowerThanZeroException {
        this(UUID.randomUUID(), name, priceInCents);
    }

    public Article(UUID id, String name, int priceInCents) throws PriceLowerThanZeroException {

        if(priceInCents< 0)
            throw new PriceLowerThanZeroException(name + " price can't be lower than zero (actual:" + priceInCents + ")");

        this.id = id;
        this.name = name;
        this.priceInCents = priceInCents;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriceInCents() {
        return priceInCents;
    }

    public void setPriceInCents(int newPrice) {
        this.priceInCents = newPrice;
    }
}
