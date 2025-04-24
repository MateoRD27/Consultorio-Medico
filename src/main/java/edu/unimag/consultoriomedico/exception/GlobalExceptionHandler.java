package edu.unimag.consultoriomedico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Maneja las excepciones lanzadas cuando fallan las validaciones de los datos de entrada (por ejemplo, @NotNull, @Email, etc.)
    // Devuelve un error 400 (BAD REQUEST) con el mensaje de la excepción.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed")
                .errors(fieldErrors)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    // Maneja las excepciones IllegalArgumentException, que suelen lanzarse cuando un argumento proporcionado no es válido.
    // Devuelve un error 400 (BAD REQUEST) con el mensaje de la excepción.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    // Maneja cualquier excepción genérica que no haya sido capturada por los manejadores específicos.
    // Devuelve un error 500 (INTERNAL SERVER ERROR) con un mensaje genérico.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // excepciones con respecto a doctores
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    // Maneja las excepciones cuando un médico no está disponible para el horario solicitado
    @ExceptionHandler(DoctorAvailabilityException.class)
    public ResponseEntity<ApiError> handleDoctorAvailability(DoctorAvailabilityException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    //excepciones con respecto a pacientes

    // Maneja las excepciones cuando no se encuentra un paciente en el sistema
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ApiError> handlePatientNotFound(PatientNotFoundException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    //excepciones con respecto a citas

    // Maneja la excepción cuando dos citas tienen el mismo horario
    @ExceptionHandler(AppointmentTimeConflictException.class)
    public ResponseEntity<ApiError> handleAppointmentConflict(AppointmentTimeConflictException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value()) // Código HTTP 409 - Conflicto
                .message(ex.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    // Maneja la excepción cuando se intenta modificar una cita que ya ha sido completada
    @ExceptionHandler(AppointmentAlreadyCompletedException.class)
    public ResponseEntity<ApiError> handleAppointmentAlreadyCompleted(AppointmentAlreadyCompletedException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }



    //excepciones con respecto a MedicalRecords

    // Maneja la excepción cuando no se encuentra un historial médico asociado a una cita
    @ExceptionHandler(MedicalRecordNotFoundException.class)
    public ResponseEntity<ApiError> handleMedicalRecordNotFound(MedicalRecordNotFoundException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    //excepciones con respecto a ConsultRoom
}
