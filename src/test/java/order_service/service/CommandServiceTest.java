package order_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import order_service.controller.dto.CommandItemsRequest;
import order_service.domain.Article;
import order_service.domain.Command;
import order_service.exceptions.PriceLowerThanZeroException;
import order_service.repository.CommandRepository;

public class CommandServiceTest {
    
    private CommandRepository repository;
    private CommandService commandService;
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        repository = mock(CommandRepository.class);
        articleService = mock(ArticleService.class);
        commandService = new CommandService(repository, articleService);
    }

    @Test
    void shouldCreateAndStoreCommand() {
        Command command = commandService.createCommand();
        assertNotNull(command);
        verify(repository).addCommand(command);
    }

    @Test
    void shouldReturnCommandsFromRepository() {
        List<Command> expectedCommands = List.of(
            new Command(),
            new Command()
        );

        when(repository.getCommands()).thenReturn(expectedCommands);

        List<Command> actualCommands = commandService.getCommands();

        assertSame(expectedCommands, actualCommands);
        verify(repository).getCommands();
    }

    @Test 
    void add_article_in_command() throws PriceLowerThanZeroException {
        Command command = commandService.createCommand();

        Article screen = new Article("Screen", 10500);
        CommandItemsRequest commandItemsRequest = new CommandItemsRequest(screen.getId(),  5);

        when(articleService.getArticle(any())).thenReturn(screen);
        when(repository.getCommand(any())).thenReturn(command);

        assertNotNull(commandService.getCommand(command.getId()));
        assertTrue(commandService.addItems(command.getId(), List.of(commandItemsRequest)));
        assertEquals(command, commandService.getCommand(command.getId()));
        assertTrue(command.getItems().stream().anyMatch((articleInCommand) -> articleInCommand.getArticle().equals(screen)));
    }

    @Test 
    void add_article_to_unexisting_command_return_false() throws PriceLowerThanZeroException {

        CommandItemsRequest commandItemsRequest = new CommandItemsRequest(UUID.randomUUID(), 1);
        Article screen = new Article("Screen", 10500);
        
        when(articleService.getArticle(any())).thenReturn(screen);
        when(repository.getCommand(any())).thenReturn(null);

        assertFalse(commandService.addItems(UUID.randomUUID(), List.of(commandItemsRequest)));
    }

    @Test 
    void add_unexisting_article_in_command_return_false() {
        Command command = commandService.createCommand();

        CommandItemsRequest commandItemsRequest = new CommandItemsRequest(UUID.randomUUID(),  5);
        
        when(articleService.getArticle(any())).thenReturn(null);
        when(repository.getCommand(any())).thenReturn(command);

        assertFalse(commandService.addItems(command.getId(), List.of(commandItemsRequest)));
        assertTrue(command.getItems().isEmpty());
    }

    @Test 
    void add_article_with_lower_than_1_quantity_return_false() throws PriceLowerThanZeroException {
        Command command = commandService.createCommand();

        Article screen = new Article("Screen", 10500);
        CommandItemsRequest commandItemsRequest = new CommandItemsRequest(screen.getId(),  0);

        when(articleService.getArticle(any())).thenReturn(screen);
        when(repository.getCommand(any())).thenReturn(command);
        
        assertFalse(commandService.addItems(command.getId(), List.of(commandItemsRequest)));
        assertTrue(command.getItems().isEmpty());
    }

    @Test
    void add_article_if_one_fail_no_items_are_added() throws PriceLowerThanZeroException {
        Command command = commandService.createCommand();

        Article screen = new Article("Screen", 10500);
        CommandItemsRequest commandItemsRequestSucced_1 = new CommandItemsRequest(screen.getId(),  5);
        CommandItemsRequest commandItemsRequestSucced_2 = new CommandItemsRequest(screen.getId(),  5);
        CommandItemsRequest commandItemsRequestFail = new CommandItemsRequest(screen.getId(),  0);

        when(articleService.getArticle(any())).thenReturn(screen);
        when(repository.getCommand(any())).thenReturn(command);
        
        assertFalse(commandService.addItems(command.getId(), List.of(commandItemsRequestSucced_1, commandItemsRequestSucced_2, commandItemsRequestFail)));
        assertTrue(command.getItems().isEmpty());
    }
}
