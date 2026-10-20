package order_service.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import order_service.exceptions.PriceLowerThanZeroException;
import order_service.exceptions.QuantityLowerThanOneException;

public class CommandTest {
    
    private Command command;

    @BeforeEach 
    public void setup() {
        command = new Command();
    }

    @Test 
    public void create_command_test() throws PriceLowerThanZeroException, QuantityLowerThanOneException {
        assertEquals(command.getCommandStatus(), CommandStatus.Pending);
        assertTrue(command.getItems().isEmpty());
        assertEquals(command.price(), 0);

        Article keyboard = new Article("keyboard", 10);
        Article mouse = new Article("mouse", 5);
        ArticleInCommand mouseInCommand = new ArticleInCommand(mouse, 5);

        ArticleInCommand keyboardInCommand = command.addArticle(keyboard, 3);
        command.addArticle(mouseInCommand);

        assertTrue(command.getItems().contains(mouseInCommand));
        assertTrue(command.getItems().contains(keyboardInCommand));

        assertEquals(command.price(), 10 * 3 + 5 * 5);
    }

    @Test 
    public void add_items_with_zero_or_less_quantity_raise_exception_and_the_items_list_is_not_changed() throws QuantityLowerThanOneException, PriceLowerThanZeroException {
        command.addArticle(new Article("keyboard", 10), 10);
        
        assertThrows(QuantityLowerThanOneException.class, () -> command.addArticle(new Article("keyboard", 10), -1));
        assertThrows(QuantityLowerThanOneException.class, () -> command.addArticle(new Article("keyboard", 10), 0));

        assertEquals(command.getItems().size(), 1);
        assertEquals(command.price(), 10*10);
    }
}
