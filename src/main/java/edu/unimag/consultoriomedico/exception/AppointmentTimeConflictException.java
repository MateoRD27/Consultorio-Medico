package edu.unimag.consultoriomedico.exception;

// Se lanza cuando un doctor o consultorio ya tiene una cita en la misma hora.
public class AppointmentTimeConflictException extends RuntimeException {
    public AppointmentTimeConflictException(String message) {
        super(message);
    }
}