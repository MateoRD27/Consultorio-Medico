package edu.unimag.consultoriomedico.exception;

// Se lanza cuando se intenta modificar una cita ya completada.
public class AppointmentAlreadyCompletedException extends RuntimeException {
    public AppointmentAlreadyCompletedException(String message) {
        super(message);
    }
}