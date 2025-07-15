package com.paginainformativa.energias_asequibles.services.excepciones;

public class EntidadNoDesactivadaExcepcion extends RuntimeException {

    public EntidadNoDesactivadaExcepcion(String message) {
        super(message);
    }

    public EntidadNoDesactivadaExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
}
