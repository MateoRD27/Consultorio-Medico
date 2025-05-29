package edu.unimag.consultoriomedico.service;

import edu.unimag.consultoriomedico.dto.MedicalRecordDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicalRecordsService {
    //obtener todos los registros medicos
    List<MedicalRecordDTO> getAllMedicalRecords();
    //obtener un registro medico por id
    MedicalRecordDTO getMedicalRecordById(Long id);
    //Obtener historiales de un paciente
    List<MedicalRecordDTO> getMedicalRecordsByPatientId(Long id);
    //Crear historial clínico de una cita
    MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO);
    //Eliminar historial
    void deleteMedicalRecord(Long id);
}
