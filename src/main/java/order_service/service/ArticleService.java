package order_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import order_service.domain.Article;
import order_service.repository.ArticleRepository;

@Service 
public class ArticleService {
    
    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public List<Article> getArticles() {
        return repository.getArticles();
    }

    public Article getArticle(UUID id) {
        return repository.getArticle(id);
    }
}
