package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    //Buscar historial de un paciente
    @Query("SELECT m FROM MedicalRecord m WHERE m.patient.id = :patientId")
    List<MedicalRecord> findByPatientId(@Param("patientId") Long patientId);

    //buscar el registro medico de una cita
    Optional<MedicalRecord> findByAppointmentId(Long appointmentId);

}
