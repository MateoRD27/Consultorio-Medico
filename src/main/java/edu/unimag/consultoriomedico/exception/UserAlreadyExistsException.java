package edu.unimag.consultoriomedico.exception;

//Excepcion de que user ya existe en el sistema
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
