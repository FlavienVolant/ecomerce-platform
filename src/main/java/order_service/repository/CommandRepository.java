package order_service.repository;

import java.util.List;
import java.util.UUID;

import order_service.domain.Command;

public interface CommandRepository {
    public List<Command> getCommands();
    public Command getCommand(UUID id);
    public void addCommand(Command command);
}
