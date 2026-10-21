package order_service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import order_service.domain.Article;
import order_service.exceptions.PriceLowerThanZeroException;
import order_service.exceptions.QuantityLowerThanOneException;

public abstract class ArticleRepositoryTest {
    private ArticleRepository repository;

    public abstract ArticleRepository getRepository();

    @BeforeEach 
    public void setup() {
        repository = getRepository();
    }

    @Test 
    public void article_repository_is_empty_at_start_test() {
        assertTrue(repository.getArticles().isEmpty());
        assertNull(repository.getArticle(UUID.randomUUID()));
    }

    @Test 
    public void article_repository_add_article_test() throws QuantityLowerThanOneException, PriceLowerThanZeroException {

        Article hello = new Article("Hello", 5);
        Article world = new Article("World", 10);

        repository.addArticle(hello);
        repository.addArticle(world);

        assertTrue(repository.getArticles().contains(hello));
        assertEquals(repository.getArticle(hello.getId()), hello);

        assertTrue(repository.getArticles().contains(world));
        assertEquals(repository.getArticle(world.getId()), world);
    }
}
