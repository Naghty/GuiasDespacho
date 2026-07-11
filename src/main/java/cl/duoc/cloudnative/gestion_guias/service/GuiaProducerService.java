package cl.duoc.cloudnative.gestion_guias.service;

import cl.duoc.cloudnative.gestion_guias.config.RabbitMQConfig;
import cl.duoc.cloudnative.gestion_guias.model.GuiaDespacho;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuiaProducerService {

    private static final Logger log = LoggerFactory.getLogger(GuiaProducerService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarGuia(GuiaDespacho guia) {
        try {
            log.info("Procesando guia ID: " + guia.getId());
            
            if (guia.getTransportista() == null || guia.getTransportista().isEmpty()) {
                throw new IllegalArgumentException();
            }

            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_GUIAS, 
                RabbitMQConfig.ROUTING_KEY_OK, 
                guia
            );
            log.info("Mensaje enviado a cola 1");

        } catch (Exception e) {
            log.error("Fallo detectado. Redirigiendo a cola 2");
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_GUIAS, 
                RabbitMQConfig.ROUTING_KEY_FAIL, 
                guia
            );
        }
    }
}