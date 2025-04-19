package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.MedicalRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository {
    //Buscar historial de un paciente
    @Query("SELECT m FROM MedicalRecord m WHERE m.patient.id = :patientId")
    List<MedicalRecord> findByPatientId(@Param("patientId") Long patientId);

    Optional<MedicalRecord> findByAppointmentId(Long appointmentId);

}
