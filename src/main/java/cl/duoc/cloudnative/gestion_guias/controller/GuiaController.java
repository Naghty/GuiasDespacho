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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Message;
import java.nio.charset.StandardCharsets;

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
        try {
            Message mensajeRaw = rabbitTemplate.receive("cola.definitiva.envio");
            
            if (mensajeRaw != null) {
                String jsonStr = new String(mensajeRaw.getBody(), StandardCharsets.UTF_8);
                System.out.println("Mensaje recuperado de la cola: " + jsonStr);
                return ResponseEntity.ok("Mensaje consumido con éxito: " + jsonStr);
            } else {
                return ResponseEntity.ok("No hay mensajes pendientes en la cola (la cola está vacía).");
            }
        } catch (Exception e) {
            System.err.println("Error controlado al consumir: " + e.getMessage());
            return ResponseEntity.status(500).body("Error interno en el broker: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/subir-s3")
    public ResponseEntity<String> subirS3(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío");
            }
            return ResponseEntity.ok("Archivo subido exitosamente a S3 para la guía ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir archivo a S3: " + e.getMessage());
        }
    }
}