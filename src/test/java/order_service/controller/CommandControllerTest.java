package order_service.controller;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import order_service.domain.Command;
import order_service.domain.CommandStatus;
import order_service.service.CommandService;

@WebMvcTest(CommandController.class)
@AutoConfigureRestTestClient 
public class CommandControllerTest {
    
    @Autowired 
    private RestTestClient restTestClient;

    @MockitoBean 
    private CommandService service;
    
    @Test 
    public void get_api_commands_return_200() {

        List<Command> result = List.of();

        when(service.getCommands()).thenReturn(result);

        restTestClient.get().uri("/api/commands")
                            .exchange()
                            .expectStatus().isEqualTo(200)
                            .expectBody().json("[]");
    }

    @Test
    public void get_api_commands_id_return_404_when_the_id_is_not_corresponding_to_a_command() {

        UUID id = UUID.randomUUID();
        when(service.getCommand(id)).thenReturn(null);

        restTestClient.get().uri("/api/commands/{id}", id)
                            .exchange()
                            .expectStatus()
                            .isEqualTo(404);
    }

    @Test
    public void post_api_commands_return_201_when_a_command_is_created() {

        Command command = new Command();
        when(service.createCommand()).thenReturn(command);

        restTestClient.post().uri("/api/commands")
                            .exchange()
                            .expectStatus().isEqualTo(201)
                            .expectBody()
                            .jsonPath("$.id").isEqualTo(command.getId().toString())
                            .jsonPath("$.commandStatus").isEqualTo(CommandStatus.Pending.toString())
                            .jsonPath("$.items").isEqualTo(List.of());
    }

    @Test
    public void get_api_commands_id_return_200_when_the_id_is_corresponding_to_a_command() {

        Command command = new Command();
        UUID id = command.getId();

        when(service.getCommand(id)).thenReturn(command);

        restTestClient.get().uri("/api/commands/{id}", id)
                            .exchange()
                            .expectStatus().isEqualTo(200)
                            .expectBody()
                            .jsonPath("$.id").isEqualTo(id.toString())
                            .jsonPath("$.commandStatus").isEqualTo(CommandStatus.Pending.toString())
                            .jsonPath("$.items").isEqualTo(List.of());
    }

    @Test
    void get_api_commands_return_commands() {
        Command command1 = new Command();
        Command command2 = new Command();

        when(service.getCommands()).thenReturn(List.of(command1, command2));

        restTestClient.get()
                .uri("/api/commands")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(command1.getId().toString())
                .jsonPath("$[1].id").isEqualTo(command2.getId().toString());
    }
}
