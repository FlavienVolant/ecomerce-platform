package order_service.repository;

import java.util.List;
import java.util.UUID;

import order_service.domain.Article;

public interface ArticleRepository {
    public List<Article> getArticles();
    public Article getArticle(UUID id);
    public void addArticle(Article article);
}
