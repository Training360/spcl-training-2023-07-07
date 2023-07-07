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

 