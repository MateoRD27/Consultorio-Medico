package edu.unimag.consultoriomedico.exception;

//excepción cuando se intenta agregar una historia clínica a una cita no finalizada
public class AppointmentNotCompletedException extends RuntimeException {
    public AppointmentNotCompletedException(String message) {
        super(message);
    }
}
