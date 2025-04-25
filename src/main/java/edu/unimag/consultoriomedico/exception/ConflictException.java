package edu.unimag.consultoriomedico.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {
    private final String conflictField;

    public ConflictException(String conflictField, String message) {
        super(message);
        this.conflictField = conflictField;
    }

}