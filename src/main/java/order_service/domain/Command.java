package order_service.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import order_service.exceptions.QuantityLowerThanOneException;

public class Command {
    private UUID id;
    private List<ArticleInCommand> items;
    private CommandStatus status;

    public Command() {
        this(UUID.randomUUID());
    }

    public Command(UUID id) {
        this.id = id;
        this.items = new ArrayList<>();
        this.status = CommandStatus.Pending;
    }

    public UUID getId() {
        return id;
    }

    public List<ArticleInCommand> getItems() {
        return items;
    }

    public void addArticle(ArticleInCommand article) {
        items.add(article);
    }

    public ArticleInCommand addArticle(Article article, int quantity) throws QuantityLowerThanOneException {
        ArticleInCommand articleInCommand = new ArticleInCommand(article, quantity);
        this.addArticle(articleInCommand);
        return articleInCommand;
    }

    public void removeArticle(ArticleInCommand article) {
        items.remove(article);
    }

    public CommandStatus getCommandStatus() {
        return status;
    }

    public int price() {
        return items
            .stream()
            .mapToInt(ArticleInCommand::price)
            .sum();
    }
}
