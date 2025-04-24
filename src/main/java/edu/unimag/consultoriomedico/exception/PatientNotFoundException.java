package edu.unimag.consultoriomedico.exception;

// Se lanza cuando un paciente no es encontrado en el sistema.
public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}