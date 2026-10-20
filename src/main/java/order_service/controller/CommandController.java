package order_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import order_service.domain.Command;
import order_service.service.CommandService;

@RequestMapping("/api/commands")
@RestController
public class CommandController {

    private final CommandService service;

    public CommandController(CommandService service){
        this.service = service;
    }
    

    @PostMapping
    public ResponseEntity<Command> createCommand() {
        return new ResponseEntity<>(service.createCommand(), HttpStatusCode.valueOf(201));
    }

    @GetMapping 
    public List<Command> getCommands() {
        return service.getCommands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Command> getCommand(@PathVariable UUID id) {

        Command c = service.getCommand(id);

        if(c == null)
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        return new ResponseEntity<>(c, HttpStatusCode.valueOf(200));
    }
}
