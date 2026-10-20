package order_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import order_service.domain.Command;
import order_service.repository.CommandRepository;

@Service 
public class CommandService {

    private final CommandRepository repository;

    public CommandService(CommandRepository repository) {
        this.repository = repository;
    }

    public Command addCommand() {
        Command command = new Command();
        this.repository.addCommand(command);
        return command;
    }

    public List<Command> getCommands() {
        return repository.getCommands();
    }
    
}
