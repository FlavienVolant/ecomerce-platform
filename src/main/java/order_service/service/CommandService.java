package order_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import order_service.controller.dto.CommandItemsRequest;
import order_service.domain.Article;
import order_service.domain.ArticleInCommand;
import order_service.domain.Command;
import order_service.exceptions.QuantityLowerThanOneException;
import order_service.repository.CommandRepository;

@Service 
public class CommandService {

    private final CommandRepository repository;
    private final ArticleService articleService;

    public CommandService(CommandRepository repository, ArticleService articleService) {
        this.repository = repository;
        this.articleService = articleService;
    }

    public Command createCommand() {
        Command command = new Command();
        this.repository.addCommand(command);
        return command;
    }

    public List<Command> getCommands() {
        return repository.getCommands();
    }

    public Command getCommand(UUID id) {
        return repository.getCommand(id);
    }

    public boolean addItems(UUID commandId, List<CommandItemsRequest> items) {
        Command command = repository.getCommand(commandId);

        if (command == null)
            return false;

        try {
            List<ArticleInCommand> articlesInCommand = items.stream()
            .map((item) -> {
                Article article = articleService.getArticle(item.articleId());
                if(article == null)
                    throw new IllegalArgumentException("Article not found");
                try {
                    return new ArticleInCommand(article, item.quantity());
                } catch (QuantityLowerThanOneException e) {
                    throw new IllegalArgumentException(e);
                }
            })
            .toList();   

            articlesInCommand.forEach(command::addArticle);

        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
