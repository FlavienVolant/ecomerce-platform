package order_service.repository;

public class InMemoryArticleRepositoryTest extends ArticleRepositoryTest {

    @Override
    public ArticleRepository getRepository() {
        return new InMemoryArticleRepository();
    }
}
