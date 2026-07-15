package cl.duoc.cdy2204.formativa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.duoc.cdy2204.formativa.config.RabbitMQConfig;
import cl.duoc.cdy2204.formativa.dto.ResumenInscripcionMessage;
import cl.duoc.cdy2204.formativa.dto.ResumenInscripcionMqProducerResponse;
import cl.duoc.cdy2204.formativa.dto.ResumenInscripcionMqResponse;
import cl.duoc.cdy2204.formativa.entity.Inscripcion;
import cl.duoc.cdy2204.formativa.entity.ResumenInscripcionMq;
import cl.duoc.cdy2204.formativa.repository.ResumenInscripcionMqRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@ExtendWith(MockitoExtension.class)
class ResumenInscripcionMqServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ResumenInscripcionMqRepository resumenInscripcionMqRepository;

    @Test
    void enviarResumenPublicaMensajeEnRabbitMq() {
        ResumenInscripcionMqService service = service();
        Inscripcion inscripcion = inscripcion();

        service.enviarResumen(inscripcion, "contenido resumen");

        ArgumentCaptor<ResumenInscripcionMessage> captor = ArgumentCaptor.forClass(ResumenInscripcionMessage.class);
        verify(rabbitTemplate).convertAndSend(
                eq(RabbitMQConfig.RESUMEN_EXCHANGE),
                eq(RabbitMQConfig.RESUMEN_ROUTING_KEY),
                captor.capture()
        );
        assertThat(captor.getValue().getNumeroResumen()).isEqualTo(10L);
        assertThat(captor.getValue().getContenidoResumen()).isEqualTo("contenido resumen");
    }

    @Test
    void consumirYGuardarResumenPersisteMensajeRecibido() {
        ResumenInscripcionMqService service = service();
        ResumenInscripcionMessage mensaje = mensaje();

        when(rabbitTemplate.receiveAndConvert(RabbitMQConfig.RESUMEN_QUEUE)).thenReturn(mensaje);
        when(resumenInscripcionMqRepository.save(org.mockito.ArgumentMatchers.any(ResumenInscripcionMq.class)))
                .thenAnswer(invocation -> {
                    ResumenInscripcionMq resumen = invocation.getArgument(0);
                    resumen.setId(1L);
                    return resumen;
                });

        Optional<ResumenInscripcionMqResponse> response = service.consumirYGuardarResumen();

        assertThat(response).isPresent();
        assertThat(response.get().getId()).isEqualTo(1L);
        assertThat(response.get().getNumeroResumen()).isEqualTo(10L);
        assertThat(response.get().getEstudianteEmail()).isEqualTo("maria.perez@duocuc.cl");
    }

    @Test
    void consumirYGuardarResumenRetornaVacioSinMensajes() {
        ResumenInscripcionMqService service = service();

        when(rabbitTemplate.receiveAndConvert(RabbitMQConfig.RESUMEN_QUEUE)).thenReturn(null);

        Optional<ResumenInscripcionMqResponse> response = service.consumirYGuardarResumen();

        assertThat(response).isEmpty();
    }

    @Test
    void responseProductorExponeDatosDeRabbitMq() {
        ResumenInscripcionMqService service = service();

        ResumenInscripcionMqProducerResponse response = service.responseProductor(10L);

        assertThat(response.getNumeroResumen()).isEqualTo(10L);
        assertThat(response.getCola()).isEqualTo(RabbitMQConfig.RESUMEN_QUEUE);
        assertThat(response.getExchange()).isEqualTo(RabbitMQConfig.RESUMEN_EXCHANGE);
        assertThat(response.getRoutingKey()).isEqualTo(RabbitMQConfig.RESUMEN_ROUTING_KEY);
    }

    private ResumenInscripcionMqService service() {
        return new ResumenInscripcionMqService(
                rabbitTemplate,
                resumenInscripcionMqRepository,
                RabbitMQConfig.RESUMEN_EXCHANGE,
                RabbitMQConfig.RESUMEN_QUEUE
        );
    }

    private Inscripcion inscripcion() {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(10L);
        inscripcion.setEstudianteNombre("Maria Perez");
        inscripcion.setEstudianteEmail("maria.perez@duocuc.cl");
        inscripcion.setFechaInscripcion(LocalDateTime.of(2026, 7, 6, 12, 0));
        inscripcion.setTotal(new BigDecimal("120000"));
        return inscripcion;
    }

    private ResumenInscripcionMessage mensaje() {
        ResumenInscripcionMessage mensaje = new ResumenInscripcionMessage();
        mensaje.setNumeroResumen(10L);
        mensaje.setEstudianteNombre("Maria Perez");
        mensaje.setEstudianteEmail("maria.perez@duocuc.cl");
        mensaje.setFechaInscripcion(LocalDateTime.of(2026, 7, 6, 12, 0));
        mensaje.setTotal(new BigDecimal("120000"));
        mensaje.setContenidoResumen("contenido resumen");
        return mensaje;
    }
}
