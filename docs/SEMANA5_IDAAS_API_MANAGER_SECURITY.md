# Semana 5 - IDaaS, API Manager y seguridad

Este documento resume la configuracion que se reutiliza en la entrega final EFT.
El objetivo es demostrar que el backend Spring Boot queda protegido con JWT
emitidos por Azure AD B2C y que los endpoints se exponen mediante AWS API
Gateway.

## Azure AD B2C

1. Crear o usar un tenant de Azure AD B2C.
2. Registrar una aplicacion para obtener el issuer del flujo de usuario.
3. Crear un user flow de inicio de sesion y registro.
4. Copiar el issuer del flujo y guardarlo como variable:

```bash
export AZURE_B2C_ISSUER_URI='https://tenant.b2clogin.com/.../v2.0/'
```

5. En GitHub Actions guardar el mismo valor como secret:

```text
AZURE_B2C_ISSUER_URI
```

La aplicacion valida los JWT con Spring Security y OAuth2 Resource Server.
Todos los endpoints requieren `Authorization: Bearer <token>`.

## API Gateway

1. Crear una API HTTP en AWS API Gateway.
2. Crear una integracion HTTP hacia la URL publica de EC2:

```text
http://EC2_PUBLIC_IP:8080
```

3. Registrar las rutas principales:

```text
GET    /cursos
POST   /cursos
POST   /inscripciones
GET    /inscripciones/{numeroResumen}/resumen
POST   /inscripciones/{numeroResumen}/resumenes-mq/producir
POST   /inscripciones/resumenes-mq/consumir
POST   /s3/uploadResumen
PUT    /s3/updateResumen
GET    /s3/downloadResumen
DELETE /s3/deleteResumen
```

4. Desplegar la API en el stage principal.
5. Usar la URL de invocacion en Postman o en el cliente HTML ubicado en
   `docs/frontend/index.html`.

## Validacion

Probar primero sin token. La respuesta esperada es `401 Unauthorized`.

Luego probar con un JWT vigente de Azure AD B2C. La respuesta esperada es:

- `200 OK` para consultas y descargas.
- `201 Created` para creacion de cursos e inscripciones.
- `200 OK` para operaciones de S3 y RabbitMQ.

## Evidencias sugeridas

- Captura de Azure AD B2C con el user flow.
- Captura del secret `AZURE_B2C_ISSUER_URI` configurado en GitHub.
- Captura de API Gateway con las rutas registradas.
- Captura de Postman o cliente HTML usando la URL de API Gateway con JWT.
- Captura de una llamada sin token respondiendo `401`.
