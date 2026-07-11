package cl.duoc.cloudnative.gestion_guias.model;

public class GuiaDespacho {
    
    private Long id;
    private String numeroGuia;
    private String detalle;
    private String transportista;

    public GuiaDespacho() {
    }

    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getNumeroGuia() { 
        return numeroGuia; 
    }
    
    public void setNumeroGuia(String numeroGuia) { 
        this.numeroGuia = numeroGuia; 
    }
    
    public String getDetalle() { 
        return detalle; 
    }
    
    public void setDetalle(String detalle) { 
        this.detalle = detalle; 
    }

    public String getTransportista() {
        return transportista;
    }

    public void setTransportista(String transportista) {
        this.transportista = transportista;
    }
}