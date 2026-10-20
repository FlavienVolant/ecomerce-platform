package order_service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import order_service.domain.Article;
import order_service.domain.Command;
import order_service.exceptions.PriceLowerThanZeroException;
import order_service.exceptions.QuantityLowerThanOneException;

public abstract class CommandRepositoryTest {
    
    private CommandRepository repository;

    public abstract CommandRepository getRepository();

    @BeforeEach 
    public void setup() {
        repository = getRepository();
    }

    @Test 
    public void command_repository_is_empty_at_start_test() {
        assertTrue(repository.getCommands().isEmpty());
        assertNull(repository.getCommand(UUID.randomUUID()));
    }

    @Test 
    public void command_repository_add_command_test() throws QuantityLowerThanOneException, PriceLowerThanZeroException {

        Command command = new Command();
        command.addArticle(new Article("Hello", 5), 3);
        command.addArticle(new Article("World", 10), 1);

        repository.addCommand(command);

        assertTrue(repository.getCommands().contains(command));
        assertEquals(repository.getCommand(command.getId()), command);
    }
}

