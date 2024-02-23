# BCI Challenge Ivan Unterberger Bauni

## Como correr
En el directorio base del proyecto agregar un archivo .env con la expresión
regular de la contraseña con clave 
``` powershell
PASSWORD_REGEX
```
Archivo de ejemplo **ENV_EXAMPLE** (Es posible cambiar el nombre y 
utilizar directamente).

Una vez creado el archivo ya es posible correr el proyecto colocandose en el
directorio base e ingresando en consola el siguiente comando
``` powershell
./mvnw spring-boot:run
```
Una vez comenzada la aplicacion se abrirá en el puerto 8080.

No es necesario correr ningún script para popular la base de datos.

## Swagger UI
La aplicación cuenta con swagger, para ingresar solamente correr el proyecto
y dirigirse al siguiente link: http://localhost:8080/swagger-ui/#/

## H2 Console
### para acceder a la consola H2 ir a http://localhost:8080/console
- Password = "password"
- JDBC URL = "jdbc:h2:mem:testdb"
