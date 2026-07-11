package cl.duoc.cloudnative.gestion_guias.service;

import cl.duoc.cloudnative.gestion_guias.model.GuiaDespacho;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuiaProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarGuia(GuiaDespacho guia) {
        System.out.println("Procesando guia ID: " + guia.getId());
        rabbitTemplate.convertAndSend("exchange.guias", "routing.guias", guia);
        System.out.println("Mensaje enviado a cola 1");
    }
}