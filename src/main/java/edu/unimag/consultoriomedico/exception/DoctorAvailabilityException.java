package edu.unimag.consultoriomedico.exception;


// Se lanza cuando se intenta agendar una cita fuera del horario disponible del médico.
public class DoctorAvailabilityException extends RuntimeException {
    public DoctorAvailabilityException(String message) {
        super(message);
    }
}
