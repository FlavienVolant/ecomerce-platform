package order_service.domain;

import order_service.exceptions.QuantityLowerThanOneException;

public class ArticleInCommand {
    private Article article;
    private int quantity;
    private int unitPriceInCents;

    public ArticleInCommand(Article article, int quantity) throws QuantityLowerThanOneException {

        if(quantity < 1)
            throw new QuantityLowerThanOneException("Quantity for " + article.getName() + " can't be lower than 1 (actual: " + quantity + ")") ;

        this.article = article;
        this.quantity = quantity;
        this.unitPriceInCents = article.getPriceInCents();
    }

    public Article getArticle() {
        return article;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitPriceInCents() {
        return unitPriceInCents;
    }

    public int price() {
        return quantity * unitPriceInCents;
    }
}
