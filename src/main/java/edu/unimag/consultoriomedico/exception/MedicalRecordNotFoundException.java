package edu.unimag.consultoriomedico.exception;

// Se lanza cuando se intenta registrar un historial médico para una cita no completada.
public class MedicalRecordNotFoundException extends RuntimeException {
    public MedicalRecordNotFoundException(String message) {
        super(message);
    }
}