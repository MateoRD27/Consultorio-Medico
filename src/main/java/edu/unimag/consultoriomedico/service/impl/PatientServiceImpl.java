package edu.unimag.consultoriomedico.service.impl;

import edu.unimag.consultoriomedico.dto.PatientDTO;
import edu.unimag.consultoriomedico.entity.Patient;
import edu.unimag.consultoriomedico.exception.ResourceNotFoundException;
import edu.unimag.consultoriomedico.exception.UserAlreadyExistsException;
import edu.unimag.consultoriomedico.mapper.PatientMapper;
import edu.unimag.consultoriomedico.repository.PatientRepository;
import edu.unimag.consultoriomedico.service.PatientService;

import java.util.List;

public class PatientServiceImpl implements PatientService {

    PatientRepository patientRepository;
    PatientMapper patientMapper;
    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDTO)
                .toList();
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDTO).orElseThrow(()->new ResourceNotFoundException("Patient not found"));
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDto) {
        //verificar si el patient ya existe
        if(patientRepository.findByIdentificationNumber(patientDto.getIdentificationNumber()).isPresent()) {
            throw new UserAlreadyExistsException("Patient already exists with identification number: " + patientDto.getIdentificationNumber());
        }
        Patient patient = patientMapper.toEntity(patientDto);
        return patientMapper.toDTO(patientRepository.save(patient));
    }

    @Override
    public PatientDTO updatePatient(Long id, PatientDTO patientDto) {
        return null;
    }

    @Override
    public void deletePatient(Long id) {

    }
}
