package edu.unimag.consultoriomedico.enums;

/**
 * Enum que representa los posibles estados de una cita.
 * Se almacena en la base de datos como STRING cuando se usa en una entidad JPA
 * con @Enumerated(EnumType.STRING).
 */
public enum Status {
    SCHEDULED,   // Cita programada
    COMPLETED,   // Cita completada
    CANCELED     // Cita cancelada
}

