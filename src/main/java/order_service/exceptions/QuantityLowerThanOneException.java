package order_service.exceptions;

public class QuantityLowerThanOneException extends Exception{
    public QuantityLowerThanOneException(String errorMessage) {
        super(errorMessage);
    }
}
