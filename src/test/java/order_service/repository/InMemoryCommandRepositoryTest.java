package order_service.repository;

public class InMemoryCommandRepositoryTest extends CommandRepositoryTest{

    @Override
    public CommandRepository getRepository() {
        return new InMemoryCommandRepository();
    }
}
