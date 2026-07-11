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

@RestController
@RequestMapping("/api/guias")
public class GuiaController {

    @Autowired
    private GuiaProducerService guiaProducerService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearGuia(@RequestBody GuiaDespacho guia) {
        guiaProducerService.enviarGuia(guia);
        return ResponseEntity.ok("Guía enviada a la cola con éxito");
    }

    @GetMapping("/status")
    public ResponseEntity<String> checkStatus() {
        return ResponseEntity.ok("Servicio de Gestión de Guías Operativo");
    }
}