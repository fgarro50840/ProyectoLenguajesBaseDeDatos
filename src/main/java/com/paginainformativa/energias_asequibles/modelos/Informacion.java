package com.paginainformativa.energias_asequibles.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "informaciones")
public class Informacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "energia_id")
    private Energia energia;
    @Column(columnDefinition = "LONGTEXT")
    private String infoTexto;
    private boolean activo;

    public Informacion() {
    }

    public Informacion(Long id, Energia energia, String infoTexto, boolean activo) {
        this.id = id;
        this.energia = energia;
        this.infoTexto = infoTexto;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public String getInfoTexto() {
        return infoTexto;
    }

    public void setInfoTexto(String informacion) {
        this.infoTexto = informacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
