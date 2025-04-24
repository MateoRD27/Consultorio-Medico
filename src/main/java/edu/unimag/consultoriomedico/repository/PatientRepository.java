package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.Doctor;
import edu.unimag.consultoriomedico.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    //Buscar un doctor por su numero de identificacion
    Optional<Patient> findByIdentificationNumber(Long identificationNumber);

}
