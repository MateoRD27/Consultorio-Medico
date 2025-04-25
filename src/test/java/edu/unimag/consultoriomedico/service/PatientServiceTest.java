package edu.unimag.consultoriomedico.service;

import edu.unimag.consultoriomedico.dto.PatientDTO;
import edu.unimag.consultoriomedico.entity.Patient;
import edu.unimag.consultoriomedico.exception.ResourceNotFoundException;
import edu.unimag.consultoriomedico.exception.UserAlreadyExistsException;
import edu.unimag.consultoriomedico.mapper.PatientMapper;
import edu.unimag.consultoriomedico.repository.PatientRepository;
import edu.unimag.consultoriomedico.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    PatientDTO patientDTO;
    Patient patient;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();

        patientDTO = PatientDTO.builder()
                .fullName("Javier Florez")
                .identificationNumber(123456L)
                .email("jefer@example.com")
                .phoneNumber("1234567890")
                .build();

        patient = Patient.builder()
                .fullName("Javier Florez")
                .identificationNumber(123456L)
                .email("jefer@example.com")
                .phoneNumber("1234567890")
                .build();
    }

    // Verifica que se obtenga correctamente la lista de todos los pacientes
    @Test
    void shouldGetAllPatients() {
        when(patientRepository.findAll()).thenReturn(List.of(patient));
        when(patientMapper.toDTO(patient)).thenReturn(patientDTO);

        List<PatientDTO> result = patientService.getAllPatients();

        assertEquals(1, result.size());
        assertEquals("Javier Florez", result.getFirst().getFullName());
    }

    // Verifica que se obtenga un paciente por su ID
    @Test
    void shouldGetPatientById() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientMapper.toDTO(patient)).thenReturn(patientDTO);

        PatientDTO result = patientService.getPatientById(1L);

        assertEquals("Javier Florez", result.getFullName());
    }

    // Verifica que se cree un nuevo paciente
    @Test
    void shouldCreatePatient() {
        when(patientRepository.findByIdentificationNumber(patientDTO.getIdentificationNumber()))
                .thenReturn(Optional.empty());
        when(patientMapper.toEntity(patientDTO)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.toDTO(patient)).thenReturn(patientDTO);

        PatientDTO result = patientService.createPatient(patientDTO);

        assertEquals("Javier Florez", result.getFullName());
        verify(patientRepository).save(patient);
    }


    // Verifica que se actualicen los datos de un paciente correctamente
    @Test
    void shouldUpdatePatient() {
        Patient existing = Patient.builder()
                .id(1L)
                .fullName("Old Name")
                .identificationNumber(111L)
                .email("old@example.com")
                .phoneNumber("1111111111")
                .build();

        Patient updated = Patient.builder()
                .id(1L)
                .fullName("Javier Florez")
                .identificationNumber(123456L)
                .email("jefer@example.com")
                .phoneNumber("1234567890")
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(patientRepository.save(existing)).thenReturn(updated);
        when(patientMapper.toDTO(updated)).thenReturn(patientDTO);

        PatientDTO result = patientService.updatePatient(1L, patientDTO);

        assertEquals("Javier Florez", result.getFullName());
    }

    // Verifica que se elimine un paciente correctamente si existe
    @Test
    void shouldDeletePatient() {
        when(patientRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> patientService.deletePatient(1L));
        verify(patientRepository).deleteById(1L);
    }

    // Verifica que se lance una excepción si el paciente no existe al obtenerlo por ID
    @Test
    void shouldThrowIfPatientNotFoundForGet() {
        when(patientRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.getPatientById(999L));
    }

    // Verifica que se lance una excepción si el paciente no existe al intentar actualizarlo
    @Test
    void shouldThrowIfPatientNotFoundForUpdate() {
        when(patientRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.updatePatient(999L, patientDTO));
    }

    // Verifica que se lance una excepción si el paciente no existe al eliminarlo
    @Test
    void shouldThrowIfPatientNotFoundForDelete() {
        when(patientRepository.existsById(999L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> patientService.deletePatient(999L));
    }

    // Verifica que se lance una excepción si se intenta crear un paciente que ya existe
    @Test
    void shouldThrowIfPatientAlreadyExists() {
        when(patientRepository.findByIdentificationNumber(patientDTO.getIdentificationNumber()))
                .thenReturn(Optional.of(patient));

        assertThrows(UserAlreadyExistsException.class, () -> patientService.createPatient(patientDTO));
    }
}
