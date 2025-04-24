package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialty(String specialty);

    //Buscar todos Los doctores que estan disponibles en un horario determinado
    List<Doctor> findByAvailableFromLessThanEqualAndAvailableToGreaterThanEqual(LocalTime startTime, LocalTime endTime);

    //validar si un doctor dado su identificationNumber esta disponible en un horario determinado
    Optional<Doctor> findByIdentificationNumberAndAvailableFromLessThanEqualAndAvailableToGreaterThanEqual(Long identificationNumber, LocalTime startTime, LocalTime endTime);

    //Buscar un doctor por su numero de identificacion
    Optional<Doctor> findByIdentificationNumber(Long identificationNumber);

}
