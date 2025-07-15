package com.paginainformativa.energias_asequibles.services.excepciones;

public class EntidadNoEliminadaExcepcion extends RuntimeException {

    public EntidadNoEliminadaExcepcion(String message) {
        super(message);
    }

    public EntidadNoEliminadaExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
}
