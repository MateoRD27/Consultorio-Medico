package edu.unimag.consultoriomedico.service.impl;

import edu.unimag.consultoriomedico.dto.PatientDTO;
import edu.unimag.consultoriomedico.entity.Patient;
import edu.unimag.consultoriomedico.exception.ResourceNotFoundException;
import edu.unimag.consultoriomedico.exception.UserAlreadyExistsException;
import edu.unimag.consultoriomedico.mapper.PatientMapper;
import edu.unimag.consultoriomedico.repository.PatientRepository;
import edu.unimag.consultoriomedico.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private  final PatientRepository patientRepository;
    private  final PatientMapper patientMapper;

    //obtener todos los paciente
    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDTO)
                .toList();
    }

    //Obtener un paciente por su ID
    @Override
    public PatientDTO getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDTO)
                .orElseThrow(()->new ResourceNotFoundException("Patient not found"));
    }

    //Crear un nuevo paciente
    @Override
    public PatientDTO createPatient(PatientDTO patientDto) {
        //verificar si el patient ya existe
        if(patientRepository.findByIdentificationNumber(patientDto.getIdentificationNumber()).isPresent()) {
            throw new UserAlreadyExistsException("Patient already exists with identification number: " + patientDto.getIdentificationNumber());
        }
        Patient patient = patientMapper.toEntity(patientDto);
        return patientMapper.toDTO(patientRepository.save(patient));
    }

    //Actualizar un paciente
    @Override
    public PatientDTO updatePatient(Long id, PatientDTO patientDto) {
        return patientRepository.findById(id)
                .map(userInDB ->{
                    userInDB.setFullName(patientDto.getFullName());
                    userInDB.setIdentificationNumber(patientDto.getIdentificationNumber());
                    userInDB.setEmail(patientDto.getEmail());
                    userInDB.setPhoneNumber(patientDto.getPhoneNumber());
                    return patientMapper.toDTO(patientRepository.save(userInDB));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));
    }

    //Elimiinar un paciente
    @Override
    public void deletePatient(Long id) {
        if(!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with ID: " + id);
        }
        patientRepository.deleteById(id);
    }
}
