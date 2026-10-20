# Order Service

## How to

Unix
```
./mvnw test
./mvnw spring-boot:run
```

Windows
```
mvnw.cmd test
mvnw.cmd spring-boot:run
```

### Get

Get commands
```
curl -i http://localhost:8080/api/commands
```

Post commmand
```
curl -i -X POST http://localhost:8080/api/commands
```

Get command by UUID
```
curl -i http://localhost:8080/api/commands/825c1020-99d8-4650-83e4-2f1a8269b21d
```

