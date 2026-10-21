package order_service.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import order_service.domain.Article;

@Repository 
public class InMemoryArticleRepository implements ArticleRepository{

    private final HashMap<UUID, Article> articles = new HashMap<>();

    @Override
    public List<Article> getArticles() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public Article getArticle(UUID id) {
        return articles.get(id);
    }

    @Override
    public void addArticle(Article article) {
        articles.put(article.getId(), article);
    }
}
