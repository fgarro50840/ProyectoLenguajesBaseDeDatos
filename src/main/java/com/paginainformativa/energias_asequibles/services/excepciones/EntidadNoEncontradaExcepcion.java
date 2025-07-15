package com.paginainformativa.energias_asequibles.services.excepciones;

public class EntidadNoEncontradaExcepcion extends RuntimeException {

    public EntidadNoEncontradaExcepcion(String message) {
        super(message);
    }

    public EntidadNoEncontradaExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
}
