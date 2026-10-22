package order_service.controller;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import order_service.domain.Article;
import order_service.exceptions.PriceLowerThanZeroException;
import order_service.service.ArticleService;

@WebMvcTest(ArticleController.class)
@AutoConfigureRestTestClient 
public class ArticleControllerTest {
    
    @Autowired 
    private RestTestClient restTestClient;

    @MockitoBean 
    private ArticleService service;

    @Test 
    public void get_api_articles_return_200() throws PriceLowerThanZeroException {

        Article hello = new Article("Hello", 500);

        List<Article> result = List.of(hello);

        when(service.getArticles()).thenReturn(result);

        restTestClient.get().uri("/api/articles")
                            .exchange()
                            .expectStatus().isEqualTo(200)
                            .expectBody()
                            .jsonPath("$[0].id").isEqualTo(hello.getId().toString())
                            .jsonPath("$[0].name").isEqualTo(hello.getName())
                            .jsonPath("$[0].priceInCents").isEqualTo(hello.getPriceInCents());
    }

    @Test
    public void get_api_articles_id_return_404_when_the_id_is_not_corresponding_to_a_article() {

        UUID id = UUID.randomUUID();
        when(service.getArticle(id)).thenReturn(null);

        restTestClient.get().uri("/api/articles/{id}", id)
                            .exchange()
                            .expectStatus()
                            .isEqualTo(404);
    }

    @Test
    public void get_api_articles_id_return_200_when_the_id_is_corresponding_to_a_article() throws PriceLowerThanZeroException {

        Article hello = new Article("Hello", 500);
        UUID id = hello.getId();

        when(service.getArticle(id)).thenReturn(hello);

        restTestClient.get().uri("/api/articles/{id}", id)
                            .exchange()
                            .expectStatus().isEqualTo(200)
                            .expectBody()
                            .jsonPath("$.id").isEqualTo(hello.getId().toString())
                            .jsonPath("$.name").isEqualTo(hello.getName())
                            .jsonPath("$.priceInCents").isEqualTo(hello.getPriceInCents());
    }
}
