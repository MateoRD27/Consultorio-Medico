package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.Patient;

import java.util.Optional;

public interface PatientRepository {
    Optional<Patient> findByEmail(String email);

}
