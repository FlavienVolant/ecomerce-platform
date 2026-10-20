package order_service.exceptions;

public class PriceLowerThanZeroException extends Exception{
    public PriceLowerThanZeroException(String errorMessage) {
        super(errorMessage);
    }
}
