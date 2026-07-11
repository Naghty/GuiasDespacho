package cl.duoc.cloudnative.gestion_guias.controller;

import cl.duoc.cloudnative.gestion_guias.model.GuiaDespacho;
import cl.duoc.cloudnative.gestion_guias.service.GuiaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RestController
@RequestMapping("/api/guias")
public class GuiaController {

    @Autowired
    private GuiaProducerService guiaProducerService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/crear")
    public ResponseEntity<String> crearGuia(@RequestBody GuiaDespacho guia) {
        guiaProducerService.enviarGuia(guia);
        return ResponseEntity.ok("Guía enviada a la cola con éxito");
    }

    @GetMapping("/status")
    public ResponseEntity<String> checkStatus() {
        return ResponseEntity.ok("Servicio de Gestión de Guías Operativo");
    }

    @PostMapping("/consumir")
    public ResponseEntity<String> consumirGuia() {
        Object mensaje = rabbitTemplate.receiveAndConvert("cola.definitiva.envio");
        if (mensaje != null) {
            System.out.println(mensaje);
            return ResponseEntity.ok("Mensaje consumido desde la cola y procesado con éxito.");
        } else {
            return ResponseEntity.ok("No hay mensajes pendientes en la cola.");
        }
    }
}