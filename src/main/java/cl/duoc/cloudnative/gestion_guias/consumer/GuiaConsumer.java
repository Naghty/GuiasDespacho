package cl.duoc.cloudnative.gestion_guias.consumer;

import cl.duoc.cloudnative.gestion_guias.model.GuiaDespacho;
import cl.duoc.cloudnative.gestion_guias.model.GuiaProcesada;
import cl.duoc.cloudnative.gestion_guias.repository.GuiaProcesadaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuiaConsumer {

    @Autowired
    private GuiaProcesadaRepository guiaProcesadaRepository;

    @RabbitListener(queues = "cola.guias.envio")
    public void procesarGuia(GuiaDespacho guia) {
        try {
            GuiaProcesada procesada = new GuiaProcesada();
            procesada.setId(guia.getId());
            procesada.setEstado("PROCESADO");
            procesada.setUrlS3("https://mi-bucket-s3.s3.amazonaws.com/guias/" + guia.getId() + ".pdf");
            guiaProcesadaRepository.save(procesada);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}