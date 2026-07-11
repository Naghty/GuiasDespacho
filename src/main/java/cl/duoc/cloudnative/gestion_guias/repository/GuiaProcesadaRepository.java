package cl.duoc.cloudnative.gestion_guias.repository;

import cl.duoc.cloudnative.gestion_guias.model.GuiaProcesada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuiaProcesadaRepository extends JpaRepository<GuiaProcesada, Long> {
}