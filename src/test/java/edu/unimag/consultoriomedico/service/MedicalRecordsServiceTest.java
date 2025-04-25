package edu.unimag.consultoriomedico.service;

import edu.unimag.consultoriomedico.dto.MedicalRecordDTO;
import edu.unimag.consultoriomedico.entity.Appointment;
import edu.unimag.consultoriomedico.entity.MedicalRecord;
import edu.unimag.consultoriomedico.entity.Patient;
import edu.unimag.consultoriomedico.entity.Status;
import edu.unimag.consultoriomedico.exception.AppointmentNotCompletedException;
import edu.unimag.consultoriomedico.exception.ResourceNotFoundException;
import edu.unimag.consultoriomedico.mapper.MedicalRecordMapper;
import edu.unimag.consultoriomedico.repository.AppointmentRepository;
import edu.unimag.consultoriomedico.repository.MedicalRecordRepository;
import edu.unimag.consultoriomedico.repository.PatientRepository;
import edu.unimag.consultoriomedico.service.impl.MedicalRecordsServiceImpl;
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
class MedicalRecordsServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private MedicalRecordMapper medicalRecordMapper;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private MedicalRecordsServiceImpl service;

    MedicalRecordDTO medicalRecordDTO;
    MedicalRecord medicalRecord;
    Patient patient;
    Appointment appointment;

    @BeforeEach
    void setUp() {
        medicalRecordDTO = MedicalRecordDTO.builder().id(1L).patientId(1L).appointmentId(1L).diagnosis("Diagnosis").build();
        patient = Patient.builder().id(1L).fullName("Paciente Uno").build();
        appointment = Appointment.builder().id(1L).status(Status.COMPLETED).build();
        medicalRecord = MedicalRecord.builder().id(1L).patient(patient).appointment(appointment).diagnosis("Diagnosis").build();
    }

    // Test para obtener todos los registros médicos
    @Test
    void shouldGetAllMedicalRecords() {
        when(medicalRecordRepository.findAll()).thenReturn(List.of(medicalRecord));
        when(medicalRecordMapper.toDTO(medicalRecord)).thenReturn(medicalRecordDTO);

        List<MedicalRecordDTO> result = service.getAllMedicalRecords();

        assertEquals(1, result.size());
        assertEquals("Diagnosis", result.getFirst().getDiagnosis());
    }


    // Test para obtener un registro médico por su ID
    @Test
    void shouldGetMedicalRecordById() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(medicalRecord));
        when(medicalRecordMapper.toDTO(medicalRecord)).thenReturn(medicalRecordDTO);

        MedicalRecordDTO result = service.getMedicalRecordById(1L);
        assertEquals("Diagnosis", result.getDiagnosis());
    }

    //  obtener registros médicos por ID de paciente
    @Test
    void shouldGetMedicalRecordsByPatientId() {
        when(medicalRecordRepository.findByPatientId(1L)).thenReturn(List.of(medicalRecord));
        when(medicalRecordMapper.toDTO(medicalRecord)).thenReturn(medicalRecordDTO);

        List<MedicalRecordDTO> result = service.getMedicalRecordsByPatientId(1L);
        assertEquals(1, result.size());
    }

    // Test para crear un nuevo registro médico
    @Test
    void shouldCreateMedicalRecord() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(medicalRecordMapper.toEntity(medicalRecordDTO)).thenReturn(medicalRecord);
        when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);
        when(medicalRecordMapper.toDTO(medicalRecord)).thenReturn(medicalRecordDTO);

        MedicalRecordDTO result = service.createMedicalRecord(medicalRecordDTO);
        assertEquals("Diagnosis", result.getDiagnosis());
    }


    // Test para eliminar un registro médico
    @Test
    void shouldDeleteMedicalRecord() {
        when(medicalRecordRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteMedicalRecord(1L));
        verify(medicalRecordRepository).deleteById(1L);
    }

    // Test para verificar que se lanza excepción si no se encuentran registros médicos
    @Test
    void shouldThrowIfNoMedicalRecordsFound() {
        when(medicalRecordRepository.findAll()).thenReturn(List.of());
        assertThrows(ResourceNotFoundException.class, service::getAllMedicalRecords);
    }

    // verificar que se lanza una excepción si no se encuentra un registro médico por su ID
    @Test
    void shouldThrowIfMedicalRecordNotFoundById() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getMedicalRecordById(1L));
    }


    //  verificar que se lanza una excepción si no se encuentran registros médicos para un paciente
    @Test
    void shouldThrowIfNoMedicalRecordsByPatientId() {
        when(medicalRecordRepository.findByPatientId(1L)).thenReturn(List.of());
        assertThrows(ResourceNotFoundException.class, () -> service.getMedicalRecordsByPatientId(1L));
    }

    // verificar que se lanza una excepción si la cita no está completada
    @Test
    void shouldThrowIfAppointmentNotCompleted() {
        appointment.setStatus(Status.SCHEDULED);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        assertThrows(AppointmentNotCompletedException.class, () -> service.createMedicalRecord(medicalRecordDTO));
    }

    // verificar que se lanza una excepción si no se encuentra una cita
    @Test
    void shouldThrowIfAppointmentNotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.createMedicalRecord(medicalRecordDTO));
    }

    // verificar que se lanza una excepción si no se encuentra un paciente
    @Test
    void shouldThrowIfPatientNotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.createMedicalRecord(medicalRecordDTO));
    }



    //  verificar que se lanza una excepción si no se encuentra el registro médico para eliminar
    @Test
    void shouldThrowIfMedicalRecordNotFoundForDelete() {
        when(medicalRecordRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteMedicalRecord(1L));
    }
}
