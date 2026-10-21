package order_service;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import order_service.domain.Article;
import order_service.repository.ArticleRepository;

@Component 
public class ArticleRepositoryInitializer implements CommandLineRunner{

    private final ArticleRepository articleRepository;

    public ArticleRepositoryInitializer(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        for(Article article : List.of(
            new Article("Keyboard", 5000),
            new Article("Mouse", 2000),
            new Article("Screen", 10000)
        )) {
            articleRepository.addArticle(article);
            System.out.println(article.getName() + " " + article.getId());
        }
    }
}
