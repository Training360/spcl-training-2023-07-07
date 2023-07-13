# Spring Cloud képzés

## Spring Security basic

* start.spring.io alapján projekt generálás

## OAuth 2

```shell
docker run -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -d -p 8090:8080 --name keycloak jboss/keycloak
```

 ```
 http://localhost:8090/auth/realms/EmployeesRealm/.well-known/openid-configuration
 ```

## JWT token

## ACL

## Config Server & Client
 
```shell
docker compose up -d
```

```shell
docker run -d --cap-add=IPC_LOCK -e VAULT_DEV_ROOT_TOKEN_ID=myroot -p 8200:8200 --name vault vault:1.13.3
```

```shell
export VAULT_ADDR=http://127.0.0.1:8200
export VAULT_TOKEN=myroot
vault kv put secret/config-client-demo demo.message='Hello from Vault %s'
```

# Task  


```shell
docker run -d -e POSTGRES_DB=task -e POSTGRES_USER=task -e POSTGRES_PASSWORD=task -p 5432:5432  --name task-postgres postgres
docker exec -it task-postgres psql -U task -d task 
```

```sql
select * from task_execution
```

```shell
docker exec -it kafka-kafka-1 kafka-console-producer.sh --bootstrap-server localhost:9092 --topic taskLauncherSink-in-0
```

```json
{"applicationName": "tasks-demo", "uri": "maven:training:task-demo:0.0.1-SNAPSHOT", "commandlineArguments": []}
```

```shell
C:\java\apache-maven-3.9.3\bin\mvn.cmd install:install-file -Dfile=task-demo-0.0.1-SNAPSHOT.jar -DgroupId=training -DartifactId=task-demo -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar
```