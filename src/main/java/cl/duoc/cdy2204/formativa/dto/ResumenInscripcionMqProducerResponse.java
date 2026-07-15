package cl.duoc.cdy2204.formativa.dto;

public class ResumenInscripcionMqProducerResponse {

    private final String mensaje;
    private final Long numeroResumen;
    private final String cola;
    private final String exchange;
    private final String routingKey;

    public ResumenInscripcionMqProducerResponse(
            String mensaje,
            Long numeroResumen,
            String cola,
            String exchange,
            String routingKey
    ) {
        this.mensaje = mensaje;
        this.numeroResumen = numeroResumen;
        this.cola = cola;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Long getNumeroResumen() {
        return numeroResumen;
    }

    public String getCola() {
        return cola;
    }

    public String getExchange() {
        return exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
