# Evidencias Postman

Usar la coleccion `docs/postman_collection.json`. Antes de probar, importar la coleccion en Postman y confirmar la variable `baseUrl`:

- EC2: `http://107.23.174.145:8080`
- Local, solo si se prueba desde el computador: `http://localhost:8080`

Ejecutar las solicitudes en orden. La coleccion guarda automaticamente:

- `cursoId` desde la respuesta de `01 Crear curso`.
- `numeroResumen` desde la respuesta de `03 Crear inscripcion`.

## Capturas recomendadas

1. `01 Crear curso`: capturar metodo `POST`, ruta `/cursos`, status `201 Created` y body con `id`.
2. `02 Listar cursos`: capturar metodo `GET`, ruta `/cursos`, status `200 OK` y lista con el curso creado.
3. `03 Crear inscripcion`: capturar metodo `POST`, ruta `/inscripciones`, status `201 Created`, `inscripcionId`, cursos seleccionados y `totalPagar`.
4. `04 Descargar resumen local`: capturar metodo `GET`, ruta `/inscripciones/{numeroResumen}/resumen`, status `200 OK` y contenido del resumen.
5. `05 S3 - Subir resumen generado`: capturar metodo `POST`, ruta `/s3/uploadResumen`, status `200 OK`, bucket y clave `numeroResumen/resumen.txt`.
6. `06 S3 - Actualizar resumen generado`: capturar metodo `PUT`, ruta `/s3/updateResumen`, status `200 OK` y misma clave S3.
7. `07 S3 - Descargar resumen`: capturar metodo `GET`, ruta `/s3/downloadResumen`, status `200 OK` y contenido descargado.
8. `08 S3 - Eliminar resumen`: capturar metodo `DELETE`, ruta `/s3/deleteResumen`, status `200 OK` y clave eliminada.

## Capturas opcionales de errores

1. `09 Error validacion - Curso sin nombre`: debe responder `400 Bad Request`.
2. `10 Error negocio - Curso inexistente`: debe responder `404 Not Found`.

## Orden para la entrega

Guardar las imagenes con nombres correlativos para que la revision sea directa:

```text
01-crear-curso.png
02-listar-cursos.png
03-crear-inscripcion.png
04-descargar-resumen-local.png
05-s3-subir-resumen.png
06-s3-actualizar-resumen.png
07-s3-descargar-resumen.png
08-s3-eliminar-resumen.png
09-error-validacion.png
10-error-curso-inexistente.png
```

Para esta entrega, incluir en al menos una captura la URL `http://107.23.174.145:8080` para evidenciar que la prueba se hizo contra EC2.
