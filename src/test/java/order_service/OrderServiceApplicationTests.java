package order_service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import order_service.controller.CommandController;

@SpringBootTest
class OrderServiceApplicationTests {

	@Autowired 
	private CommandController controller;

	@Test
	void contextLoads() {
		assertNotNull(controller);
	}

}
