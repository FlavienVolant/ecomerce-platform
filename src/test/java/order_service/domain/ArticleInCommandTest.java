package order_service.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import order_service.exceptions.PriceLowerThanZeroException;
import order_service.exceptions.QuantityLowerThanOneException;

public class ArticleInCommandTest {
    
    private Article article; 

    @BeforeEach 
    public void setup() throws PriceLowerThanZeroException {
        this.article = new Article("keyboard", 10);
    }

    @Test 
    public void create_article_in_command_test() throws QuantityLowerThanOneException, PriceLowerThanZeroException {
        int quantity = 15;
        ArticleInCommand articleInCommand = new ArticleInCommand(article, quantity);

        assertEquals(articleInCommand.getUnitPriceInCents(), article.getPriceInCents());
        assertEquals(articleInCommand.getArticle(), article);
        assertEquals(articleInCommand.price(), quantity * article.getPriceInCents());
    }

    @Test 
    public void create_article_in_command_with_negative_or_zero_quantity_raise_a_QuantityLowerThanOneException() {
        assertThrows(QuantityLowerThanOneException.class, () -> new ArticleInCommand(article, 0));
        assertThrows(QuantityLowerThanOneException.class, () -> new ArticleInCommand(article, -5));
    }

    @Test 
    public void change_article_price_after_having_it_in_command_desnt_change_articleInCommand_price() throws QuantityLowerThanOneException {
        int quantity = 15; 
        ArticleInCommand articleInCommand = new ArticleInCommand(article, quantity);

        int oldArticlePrice = article.getPriceInCents();
        article.setPriceInCents(40);

        assertEquals(articleInCommand.getUnitPriceInCents(), oldArticlePrice);
        assertEquals(articleInCommand.price(), oldArticlePrice * quantity);
    }
}
