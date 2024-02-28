# BCI Challenge Ivan Unterberger Bauni

## Diagrama de secuencia de la solución

![UserController_newUser](https://github.com/iunter/bci-challenge/assets/18707501/162d0611-e373-45e7-a1b1-a87b0b02054c)


## Como correr
En el directorio base del proyecto agregar un archivo de noombre **.env** con la expresión
regular de la contraseña con clave 
``` powershell
PASSWORD_REGEX
```
Archivo de ejemplo **ENV_EXAMPLE** (Es posible cambiar el nombre y 
utilizar directamente).

Una vez creado el archivo ya es posible correr el proyecto colocándose en el
directorio base e ingresando en consola el siguiente comando
``` powershell
./gradlew bootRun
```
Una vez comenzada la aplicación se abrirá en el puerto 8080.

No es necesario correr ningún script para popular la base de datos.

## Swagger UI
La aplicación cuenta con swagger, para ingresar solamente correr el proyecto
y dirigirse al siguiente link: http://localhost:8080/swagger-ui/#/

![image](https://github.com/iunter/bci-challenge/assets/18707501/c145e677-09e7-4b94-bb50-b128beb70b3b)

Luego si se despliega el menu **user-controller** se vera lo siguiente
![image](https://github.com/iunter/bci-challenge/assets/18707501/91617058-260b-4937-b49a-2a926da14b7e)

Al seleccionar el metodo se podrá ver un ejemplo de request y un ejemplo de response
![image](https://github.com/iunter/bci-challenge/assets/18707501/6cc5f5af-d567-499e-85ed-535080119e39)

![image](https://github.com/iunter/bci-challenge/assets/18707501/9bccd9b9-f9fa-4f7c-a082-ffd6238f96e6)

Es posible realizar una prueba del endpoint clickeando en el boton **Try it out** y luego en boton **Execute**
![image](https://github.com/iunter/bci-challenge/assets/18707501/c2354131-9d10-4b72-a179-7d9ff0fc7daa)

Se verá el response abajo
![image](https://github.com/iunter/bci-challenge/assets/18707501/570e8220-2f05-4fe8-891a-6fe0c97ca297)


## H2 Console
### para acceder a la consola H2 ir a http://localhost:8080/console
- Password = "password"
- JDBC URL = "jdbc:h2:mem:testdb"
  
![image](https://github.com/iunter/bci-challenge/assets/18707501/e1962b84-c351-4c4f-ba33-a97f5c579258)

![image](https://github.com/iunter/bci-challenge/assets/18707501/64bc3730-f8ca-407b-94ab-a27c114455c1)
