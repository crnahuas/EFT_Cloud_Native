package cl.duoc.cdy2204.formativa.service;

import cl.duoc.cdy2204.formativa.config.RabbitMQConfig;
import cl.duoc.cdy2204.formativa.dto.ResumenInscripcionMessage;
import cl.duoc.cdy2204.formativa.dto.ResumenInscripcionMqResponse;
import cl.duoc.cdy2204.formativa.entity.Inscripcion;
import cl.duoc.cdy2204.formativa.entity.ResumenInscripcionMq;
import cl.duoc.cdy2204.formativa.repository.ResumenInscripcionMqRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResumenInscripcionMqService {

    private final RabbitTemplate rabbitTemplate;
    private final ResumenInscripcionMqRepository resumenInscripcionMqRepository;
    private final String exchangeName;
    private final String queueName;

    public ResumenInscripcionMqService(
            RabbitTemplate rabbitTemplate,
            ResumenInscripcionMqRepository resumenInscripcionMqRepository,
            @Value("${app.rabbitmq.resumen.exchange:" + RabbitMQConfig.RESUMEN_EXCHANGE + "}") String exchangeName,
            @Value("${app.rabbitmq.resumen.queue:" + RabbitMQConfig.RESUMEN_QUEUE + "}") String queueName
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.resumenInscripcionMqRepository = resumenInscripcionMqRepository;
        this.exchangeName = exchangeName;
        this.queueName = queueName;
    }

    public void enviarResumen(Inscripcion inscripcion, String contenidoResumen) {
        ResumenInscripcionMessage mensaje = new ResumenInscripcionMessage();
        mensaje.setNumeroResumen(inscripcion.getId());
        mensaje.setEstudianteNombre(inscripcion.getEstudianteNombre());
        mensaje.setEstudianteEmail(inscripcion.getEstudianteEmail());
        mensaje.setFechaInscripcion(inscripcion.getFechaInscripcion());
        mensaje.setTotal(inscripcion.getTotal());
        mensaje.setContenidoResumen(contenidoResumen);

        rabbitTemplate.convertAndSend(exchangeName, RabbitMQConfig.RESUMEN_ROUTING_KEY, mensaje);
    }

    @Transactional
    public Optional<ResumenInscripcionMqResponse> consumirYGuardarResumen() {
        ResumenInscripcionMessage mensaje = (ResumenInscripcionMessage) rabbitTemplate.receiveAndConvert(queueName);

        if (mensaje == null) {
            return Optional.empty();
        }

        ResumenInscripcionMq resumen = new ResumenInscripcionMq();
        resumen.setNumeroResumen(mensaje.getNumeroResumen());
        resumen.setEstudianteNombre(mensaje.getEstudianteNombre());
        resumen.setEstudianteEmail(mensaje.getEstudianteEmail());
        resumen.setFechaInscripcion(mensaje.getFechaInscripcion());
        resumen.setTotal(mensaje.getTotal());
        resumen.setContenidoResumen(mensaje.getContenidoResumen());
        resumen.setFechaProcesamiento(LocalDateTime.now());

        ResumenInscripcionMq guardado = resumenInscripcionMqRepository.save(resumen);
        return Optional.of(toResponse(guardado));
    }

    private ResumenInscripcionMqResponse toResponse(ResumenInscripcionMq resumen) {
        return new ResumenInscripcionMqResponse(
                resumen.getId(),
                resumen.getNumeroResumen(),
                resumen.getEstudianteNombre(),
                resumen.getEstudianteEmail(),
                resumen.getFechaInscripcion(),
                resumen.getFechaProcesamiento()
        );
    }
}
