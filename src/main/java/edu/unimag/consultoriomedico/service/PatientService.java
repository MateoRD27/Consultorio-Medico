package edu.unimag.consultoriomedico.service;

import edu.unimag.consultoriomedico.dto.PatientDTO;

import java.util.List;

public interface PatientService {
    //Obtener todos los pacientes
    List<PatientDTO> getAllPatients();
    //Obtener paciente por ID
    PatientDTO getPatientById(Long id);
    //Registrar nuevo paciente
    PatientDTO createPatient(PatientDTO patientDto);
    // Actualizar paciente
    PatientDTO updatePatient(Long id, PatientDTO patientDto);
    //Eliminar paciente
    void deletePatient(Long id);

}
