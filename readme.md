# Order Service

## How to

Docker
```
docker compose up -d
docker compose exec -T database psql -U ecommerce -d ecommerce < src\main\resources\db\schema.sql
docker compose exec database psql -U ecommerce -d ecommerce
```


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
curl -i http://localhost:8080/api/commands/{COMMAND_UUID}
```

Get articles
```
curl -i localhost:8080/api/articles
```

Post articles in Command
```
curl -i -X POST http://localhost:8080/api/commands/{COMMAND_UUID}/items -H "Content-Type: application/json" -d "[{\"articleId\":\"{UUID_DE_L_ARTICLE_1}\",\"quantity\":3},{\"articleId\":\"{UUID_DE_L_ARTICLE_2}\",\"quantity\":2}]"
```
