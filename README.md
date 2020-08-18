# ContractsAPI

## Run

```console
docker container ls -a
docker rm -v <OLD_CONTAINERS>
./mvnw clean package dockerfile:build -Dmaven.test.skip=true
docker-compose up
docker-compose logs mysql
```

## Tests

```console
./mvnw clean test
```

## Access

http://localhost:8090/contractsapi/swagger-ui.html
