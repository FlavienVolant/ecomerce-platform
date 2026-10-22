package order_service.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import order_service.controller.dto.CommandItemsRequest;
import order_service.domain.Command;
import order_service.domain.CommandStatus;
import order_service.repository.CommandRepository;
import order_service.service.ArticleService;
import order_service.service.CommandService;

@WebMvcTest(CommandController.class)
@AutoConfigureRestTestClient 
public class CommandControllerTest {
    
    @Autowired 
    private RestTestClient restTestClient;

    @MockitoBean 
    private CommandService commandService;

    @MockitoBean 
    private ArticleService articleService;

    @MockitoBean 
    private CommandRepository commandRepository;
    
    @Test 
    public void get_api_commands_return_200() {

        List<Command> result = List.of();

        when(commandService.getCommands()).thenReturn(result);

        restTestClient.get().uri("/api/commands")
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody().json("[]");
    }

    @Test
    public void get_api_commands_id_return_404_when_the_id_is_not_corresponding_to_a_command() {

        UUID id = UUID.randomUUID();
        when(commandService.getCommand(id)).thenReturn(null);

        restTestClient.get().uri("/api/commands/{id}", id)
                            .exchange()
                            .expectStatus().isNotFound();
    }

    @Test
    public void post_api_commands_return_201_when_a_command_is_created() {

        Command command = new Command();
        when(commandService.createCommand()).thenReturn(command);

        restTestClient.post().uri("/api/commands")
                            .exchange()
                            .expectStatus().isCreated()
                            .expectBody()
                            .jsonPath("$.id").isEqualTo(command.getId().toString())
                            .jsonPath("$.commandStatus").isEqualTo(CommandStatus.Pending.toString())
                            .jsonPath("$.items").isEqualTo(List.of());
    }

    @Test
    public void get_api_commands_id_return_200_when_the_id_is_corresponding_to_a_command() {

        Command command = new Command();
        UUID id = command.getId();

        when(commandService.getCommand(id)).thenReturn(command);

        restTestClient.get().uri("/api/commands/{id}", id)
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody()
                            .jsonPath("$.id").isEqualTo(id.toString())
                            .jsonPath("$.commandStatus").isEqualTo(CommandStatus.Pending.toString())
                            .jsonPath("$.items").isEqualTo(List.of());
    }

    @Test
    void get_api_commands_return_commands() {
        Command command1 = new Command();
        Command command2 = new Command();

        when(commandService.getCommands()).thenReturn(List.of(command1, command2));

        restTestClient.get()
                .uri("/api/commands")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(command1.getId().toString())
                .jsonPath("$[1].id").isEqualTo(command2.getId().toString());
    }

    @Test
    void post_api_commands_id_items_add_the_items_in_the_corresponding_command() {

        UUID commandId = UUID.randomUUID();

        List<CommandItemsRequest> items = List.of(
            new CommandItemsRequest(UUID.randomUUID(), 3),
            new CommandItemsRequest(UUID.randomUUID(), 5),
            new CommandItemsRequest(UUID.randomUUID(), 1)
        );

        when(commandService.addItems(commandId, items)).thenReturn(true);

        restTestClient.post()
                .uri("/api/commands/{id}/items", commandId)
                .body(items)
                .exchange()
                .expectStatus().isNoContent();

        verify(commandService).addItems(commandId, items);
    }
}
