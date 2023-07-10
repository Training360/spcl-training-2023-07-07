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