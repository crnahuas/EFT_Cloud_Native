# Formativa 1 Cloud Native - CDY2204

Este repositorio contiene la solucion desarrollada para la actividad formativa "Desplegando aplicaciones en la nube" de la asignatura Desarrollo Cloud Native. El objetivo fue construir un microservicio REST simple, demostrable y alineado a la pauta: listar cursos, agregar cursos, inscribir estudiantes, calcular el total de inscripcion y persistir la informacion en Oracle Cloud.

La aplicacion no incluye frontend ni autenticacion, porque esos puntos no forman parte del caso solicitado. El foco del trabajo esta en el backend, la persistencia cloud, la imagen Docker y el despliegue automatico hacia AWS EC2 mediante GitHub Actions.

## Endpoints requeridos

| Metodo | Ruta | Funcion |
| --- | --- | --- |
| GET | `/cursos` | Consulta cursos disponibles con nombre, instructor, duracion y costo. |
| POST | `/cursos` | Agrega un nuevo curso y lo persiste en Oracle Cloud. |
| POST | `/inscripciones` | Inscribe un estudiante en uno o mas cursos, calcula el total y persiste la inscripcion en Oracle Cloud. |

El flujo de uso esperado es crear uno o mas cursos, revisar la lista disponible y luego generar una inscripcion utilizando los IDs de los cursos existentes.

## Tecnologias

- Java 21
- Spring Boot 4.0.6
- Maven
- Spring Web
- Spring Data JPA
- Oracle JDBC
- Docker
- Docker Hub
- GitHub Actions
- AWS EC2
- Oracle Cloud Database
- Postman

La eleccion de estas tecnologias responde directamente a la actividad: Spring Boot y Maven para el microservicio, Oracle Cloud para persistencia, Docker/Docker Hub para empaquetado y publicacion, GitHub Actions para CI/CD y AWS EC2 para ejecutar el contenedor publicado.

## Ejecutar localmente

Para ejecutar localmente se debe tener disponible el wallet de Oracle Cloud. En este proyecto se utiliza el alias `procesobasedatos_high`, definido dentro del archivo `tnsnames.ora` del wallet.

Configurar variables de entorno:

```bash
mkdir -p wallet
unzip -o Wallet_ProcesoBaseDatos.zip -d wallet

export ORACLE_WALLET_PATH='./wallet'
export ORACLE_DB_URL='jdbc:oracle:thin:@procesobasedatos_high?TNS_ADMIN=./wallet'
export ORACLE_DB_USERNAME='ADMIN'
export ORACLE_DB_PASSWORD='TU_PASSWORD_ORACLE'
```

Compilar y ejecutar pruebas:

```bash
mvn clean package
```

Ejecutar:

```bash
java -jar target/formativa-cloud-native-0.0.1-SNAPSHOT.jar
```

## Probar con curl

Crear curso:

```bash
curl --location 'http://localhost:8080/cursos' \
  --header 'Content-Type: application/json' \
  --data '{
    "nombre": "Spring Boot Cloud Native",
    "instructor": "Docente CDY2204",
    "duracion": "24 horas",
    "costo": 120000
  }'
```

Listar cursos:

```bash
curl --location 'http://localhost:8080/cursos'
```

Inscribir estudiante:

```bash
curl --location 'http://localhost:8080/inscripciones' \
  --header 'Content-Type: application/json' \
  --data '{
    "estudianteNombre": "Maria Perez",
    "estudianteEmail": "maria.perez@duocuc.cl",
    "cursoIds": [1]
  }'
```

## Docker local

La imagen Docker incluye el JAR de la aplicacion y el wallet necesario para conectarse a Oracle Cloud. La password se entrega siempre por variable de entorno.

```bash
docker build -t formativa-cloud-native:1.0 .
docker run -d \
  --name formativa-cloud-native \
  -p 8080:8080 \
  -e ORACLE_DB_USERNAME='ADMIN' \
  -e ORACLE_DB_PASSWORD='TU_PASSWORD_ORACLE' \
  formativa-cloud-native:1.0
```

Para crear una imagen manual compatible con una EC2 x86_64 desde Mac Apple Silicon:

```bash
docker build --platform linux/amd64 -t formativa-cloud-native:1.0 .
```

## Documentacion de entrega

La guia completa de arquitectura, configuracion cloud, pipeline, pruebas Postman, evidencias recomendadas y checklist de rubrica esta en:

[docs/ENTREGA.md](docs/ENTREGA.md)
