# Semana 7 - RabbitMQ y mensajes asincronos

## Requerimiento aplicado

La actividad pide integrar el sistema de inscripciones con RabbitMQ. La funcionalidad de generacion del resumen envia los datos de la inscripcion a una cola y un endpoint nuevo consume el mensaje para guardarlo en una tabla nueva de Oracle Cloud.

## Configuracion RabbitMQ

Levantar RabbitMQ local:

```bash
docker compose up -d rabbitmq
```

Consola de administracion:

```text
http://localhost:15672
usuario: guest
password: guest
```

La aplicacion crea automaticamente:

```text
Cola: resumen.inscripcion.queue
Exchange direct: resumen.inscripcion.exchange
Routing key: resumen.inscripcion.key
```

Variables configurables:

```bash
export RABBITMQ_HOST='localhost'
export RABBITMQ_PORT='5672'
export RABBITMQ_USERNAME='guest'
export RABBITMQ_PASSWORD='guest'
```

## Despliegue en EC2 con Docker Compose

Para EC2 se usa `docker-compose.ec2.yml`, que levanta dos contenedores:

```text
formativa-cloud-native  -> API Spring Boot en puerto 8080
formativa-rabbitmq      -> RabbitMQ Management en puerto 15672
```

En EC2, copiar el archivo de variables:

```bash
cp .env.ec2.example .env
nano .env
```

Completar los valores reales de Docker Hub, Oracle, AWS Academy y Azure B2C.

Luego ejecutar:

```bash
docker compose -f docker-compose.ec2.yml --env-file .env pull
docker compose -f docker-compose.ec2.yml --env-file .env up -d
docker compose -f docker-compose.ec2.yml ps
```

La aplicacion se conecta a RabbitMQ usando `RABBITMQ_HOST=rabbitmq`, que es el nombre del servicio dentro de Docker Compose.

Puertos que deben estar abiertos en el Security Group para la demo:

```text
22    SSH
8080  API Spring Boot
15672 Consola RabbitMQ
```

No es necesario abrir el puerto `5672` hacia internet, porque solo lo usa la app dentro de la red interna de Docker Compose.

## Tabla Oracle

La tabla nueva es `RESUMENES_INSCRIPCION_MQ` y la secuencia es `RESUMEN_MQ_SEQ`. El script actualizado esta en `docs/oracle_schema.sql`.

## Flujo de prueba

1. Crear un curso con `POST /cursos`.
2. Crear una inscripcion con `POST /inscripciones`.
3. Generar el resumen con `GET /inscripciones/{numeroResumen}/resumen`.
4. Publicar explicitamente el resumen con `POST /inscripciones/{numeroResumen}/resumenes-mq/producir`.
5. Revisar en RabbitMQ que la cola `resumen.inscripcion.queue` tenga un mensaje listo.
6. Consumir y guardar con `POST /inscripciones/resumenes-mq/consumir`.
7. Verificar en Oracle Cloud la fila insertada en `RESUMENES_INSCRIPCION_MQ`.

Todos los endpoints mantienen la seguridad JWT del proyecto.

## Evidencia para el video

Mostrar en este orden:

1. `docker compose up -d rabbitmq` y la consola RabbitMQ abierta.
2. Postman creando curso e inscripcion.
3. Postman o cliente HTML generando el resumen.
4. Postman o cliente HTML publicando el mensaje en RabbitMQ.
5. RabbitMQ mostrando el mensaje pendiente en la cola.
6. Postman o cliente HTML ejecutando el endpoint de consumo.
7. SQL Developer mostrando la fila guardada en `RESUMENES_INSCRIPCION_MQ`.
