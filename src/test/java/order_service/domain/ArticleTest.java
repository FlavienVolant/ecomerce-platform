package order_service.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import order_service.exceptions.PriceLowerThanZeroException;

public class ArticleTest {
    
    @Test
    public void create_article_test() throws PriceLowerThanZeroException {
        Article article = new Article("keyboard", 1050); // 10.50 €

        assertEquals(article.getId().getClass(), UUID.class);
        assertEquals(article.getName(), "keyboard");
        assertEquals(article.getPriceInCents(), 1050);
    }

    @Test
    public void create_article_with_negative_price_raise_a_PriceLowerThanZeroException() {
        assertThrows(PriceLowerThanZeroException.class, () -> new Article("keyboard", -10));
    }
}
