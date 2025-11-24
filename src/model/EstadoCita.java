package model;

public enum EstadoCita {
    RESERVADA,
    CANCELADA,
    COMPLETADA;

    public static EstadoCita toEstadoCita(int EstCit){
        for (EstadoCita est : EstadoCita.values()) {
            if (est.ordinal() == EstCit ) {
                return est;
            }
        }
        throw new IllegalArgumentException("Código no válido: " + EstCit);
    }
}
