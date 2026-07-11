package cl.duoc.cloudnative.gestion_guias.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "GUIA_PROCESADA")
public class GuiaProcesada {
    
    @Id
    private Long id;
    private String estado;
    private String urlS3;

    public GuiaProcesada() {
    }

    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getEstado() { 
        return estado; 
    }
    
    public void setEstado(String estado) { 
        this.estado = estado; 
    }
    
    public String getUrlS3() { 
        return urlS3; 
    }
    
    public void setUrlS3(String urlS3) { 
        this.urlS3 = urlS3; 
    }
}