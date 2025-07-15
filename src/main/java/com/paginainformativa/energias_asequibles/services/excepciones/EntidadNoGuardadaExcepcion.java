package com.paginainformativa.energias_asequibles.services.excepciones;

public class EntidadNoGuardadaExcepcion extends RuntimeException {

    public EntidadNoGuardadaExcepcion(String message) {
        super(message);
    }

    public EntidadNoGuardadaExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
}
