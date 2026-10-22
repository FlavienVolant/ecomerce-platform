package order_service.controller.dto;

import java.util.UUID;

public record CommandItemsRequest(
    UUID articleId,
    int quantity
) {
}
