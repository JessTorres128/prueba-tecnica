# 📝 Prueba Técnica ToDos

Este proyecto es una API RESTful construida con **Spring Boot** para gestionar tareas (ToDos). Incluye operaciones CRUD (`GET`, `POST`, `PUT`, `PATCH`, `DELETE`) y documentación interactiva con Swagger.

## 🚀 Tecnologías utilizadas

* Java 24
* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL
* Hibernate
* Swagger (SpringDoc OpenAPI)
* Flyway

## 📆 Estructura del proyecto

* `config/`: Archivos de configuración
* `controllers/`: Controladores REST
* `dto/`: Objetos para entrada/salida de datos
* `exception/`: Manejador de excepciones
* `model/`: Entidades JPA
* `repository/`: Interfaces de acceso a datos
* `services/`: Lógica de negocio

## 🔧 Configuración

### application.properties

```properties
spring.application.name=Prueba Tecnica ToDos
server.port=8080

# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3307/prueba_tecnica?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Swagger
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/doc/swagger-ui.html
```

> Asegúrate de tener MySQL corriendo en el puerto `3307`, que la base de datos `prueba_tecnica` esté creada y que el usuario esté correctamente configurado y, además en caso de necesitarlo cambiar el puerto del servidor (8080).

## Instalación

1. **Clonar el repositorio**

   ```bash
   git clone https://github.com/JessTorres128/prueba-tecnica.git
   cd prueba-tecnica

2. **(Opcional) Configurar el usuario y la contraseña**  
En caso de necesitarlo configura el usuario y la contraseña de la base de datos, puedes hacerlo mediante las siguientes líneas del archivo `application.properties`:

    ```properties
    spring.datasource.username=root
    spring.datasource.password=
    ```

3. **(Opcional) Cambiar el puerto de la base de datos**  
Adicionalmente, si tienes configurado tu MySQL con el puerto por defecto `3306` asegurate de cambiar el puerto de la URL de MySQL en el archivo `application.properties`.

La linea en específico es:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3307/prueba_tecnica?  useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
  ```

4. **Configurar la base de datos**  
Asegúrate de tener una base de datos MySQL corriendo en el puerto 3307 (o en el que tengas configurado) con una base llamada `prueba_tecnica`.

Puedes crearla así:

  ```sql
   CREATE DATABASE prueba_tecnica;
  ```

Una vez creada la base de datos basta con que ejecutes el programa para que flyway cree la tabla principal y con esto ya estarías listo para empezar a agregar información.


5. **(Opcional) Modificar el puerto del servidor**  
En caso de necesitarlo edita el puerto del servidor ubicado en `application.properties` para que la aplicación pueda correr sin problemas en tu entorno.
La linea en específico es:

  ```properties
  server.port=8080
  ```

## 📚 Documentación Swagger

Una vez iniciada la aplicación, puedes acceder a la documentación interactiva en:

```
http://localhost:8080/doc/swagger-ui.html
```

Desde ahí puedes probar los endpoints de forma visual y ver ejemplos de entradas/salidas.


## 📩 Endpoints principales

* `GET /api/todos`: Listar todos los ToDos
* `POST /api/todos`: Crear un nuevo ToDo
* `PUT /api/todos/{id}`: Actualizar un ToDo completo
* `PATCH /api/todos/{id}`: Actualizar el estado de un ToDo
* `DELETE /api/todos/{id}`: Eliminar un ToDo

## ✅ Validación y respuestas

Todas las respuestas validas siguen el siguiente formato:

```json
{
  "success": true,
  "data": { ... }
}
```

En caso de error:

```json
{
  "success": false,
  "message": "Descripción del error"
}
```
