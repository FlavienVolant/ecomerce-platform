package order_service.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import order_service.domain.Command;
import order_service.repository.CommandRepository;

public class CommandServiceTest {
    
    private CommandRepository repository;
    private CommandService service;

    @BeforeEach
    void setUp() {
        repository = mock(CommandRepository.class);
        service = new CommandService(repository);
    }

    @Test
    void shouldCreateAndStoreCommand() {
        Command command = service.createCommand();
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

        List<Command> actualCommands = service.getCommands();

        assertSame(expectedCommands, actualCommands);
        verify(repository).getCommands();
    }
}
