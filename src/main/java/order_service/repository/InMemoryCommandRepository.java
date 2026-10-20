package order_service.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import order_service.domain.Command;

@Repository 
public class InMemoryCommandRepository implements CommandRepository {

    private final HashMap<UUID, Command> commands = new HashMap<>();

    @Override
    public List<Command> getCommands() {
        return new ArrayList<>(commands.values());
    }

    @Override
    public Command getCommand(UUID id) {
        return commands.get(id);
    }

    @Override
    public void addCommand(Command command) {
        commands.put(command.getId(), command);
    }
}