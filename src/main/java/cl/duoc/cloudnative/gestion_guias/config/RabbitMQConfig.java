package cl.duoc.cloudnative.gestion_guias.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String COLA_PROCESAMIENTO = "cola.guias.procesamiento";
    public static final String COLA_ERRORES = "cola.guias.errores";
    public static final String EXCHANGE_GUIAS = "exchange.guias";
    public static final String ROUTING_KEY_OK = "routing.guia.ok";
    public static final String ROUTING_KEY_FAIL = "routing.guia.fail";

    @Bean
    public Queue colaProcesamiento() {
        return new Queue(COLA_PROCESAMIENTO, true);
    }

    @Bean
    public Queue colaErrores() {
        return new Queue(COLA_ERRORES, true);
    }

    @Bean
    public DirectExchange exchangeGuias() {
        return new DirectExchange(EXCHANGE_GUIAS);
    }

    @Bean
    public Binding bindingProcesamiento(Queue colaProcesamiento, DirectExchange exchangeGuias) {
        return BindingBuilder.bind(colaProcesamiento).to(exchangeGuias).with(ROUTING_KEY_OK);
    }

    @Bean
    public Binding bindingErrores(Queue colaErrores, DirectExchange exchangeGuias) {
        return BindingBuilder.bind(colaErrores).to(exchangeGuias).with(ROUTING_KEY_FAIL);
    }
}