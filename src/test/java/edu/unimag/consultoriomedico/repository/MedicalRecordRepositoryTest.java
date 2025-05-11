package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.*;
import edu.unimag.consultoriomedico.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MedicalRecordRepositoryTest {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultRoomRepository consultRoomRepository;

    Patient patient;
    Doctor doctor;
    ConsultRoom consultRoom;
    Appointment appointment;
    MedicalRecord medicalRecord;

    @BeforeEach
    void setUp() {
        // Limpiar datos anteriores
        medicalRecordRepository.deleteAll();
        appointmentRepository.deleteAll();
        patientRepository.deleteAll();
        doctorRepository.deleteAll();
        consultRoomRepository.deleteAll();

        // Crear paciente
        patient = patientRepository.save(Patient.builder()
                .fullName("Laura Méndez")
                .identificationNumber(1122334455L)
                .email("laura.mendez@example.com")
                .phoneNumber("301-555-1234")
                .build());

        // Crear doctor
        doctor = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Ramos")
                .identificationNumber(123456789L)
                .specialty("Medicina General")
                .email("doctor.ramos@example.com")
                .availableFrom(LocalTime.of(8, 0))
                .availableTo(LocalTime.of(17, 0))
                .build());

        // Crear sala de consulta
        consultRoom = consultRoomRepository.save(ConsultRoom.builder()
                .name("Consultorio A")
                .roomNumber(101)
                .floor(1)
                .description("Sala de consulta general")
                .build());

        // Crear cita
        appointment = appointmentRepository.save(Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .consultRoom(consultRoom)
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .status(Status.SCHEDULED)
                .build());

        // Crear registro médico
        medicalRecord = medicalRecordRepository.save(MedicalRecord.builder()
                .appointment(appointment)
                .patient(patient)
                .diagnosis("Dolor leve")
                .notes("Se recomienda reposo y control en 3 días")
                .build());
    }


    // Test para verificar que el método findByPatientId devuelve los registros médicos del paciente correctamente.
    @Test
    void shouldFindByPatientId() {
        // Buscar historial médico por paciente
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByPatientId(patient.getId());

        assertNotNull(medicalRecords);
        assertFalse(medicalRecords.isEmpty());
        assertEquals(1, medicalRecords.size());
        assertEquals(patient.getId(), medicalRecords.get(0).getPatient().getId());
    }

    // Test para verificar que el método findByAppointmentId devuelve el registro médico asociado a una cita específica.
    @Test
    void testFindByAppointmentId() {
        // Buscar registro médico por cita
        Optional<MedicalRecord> foundMedicalRecord = medicalRecordRepository.findByAppointmentId(appointment.getId());

        assertTrue(foundMedicalRecord.isPresent());
        assertEquals(appointment.getId(), foundMedicalRecord.get().getAppointment().getId());
    }

    // Test para verificar que el método findByAppointmentId devuelve un valor vacío si no se encuentra un registro médico para una cita.
    @Test
    void shouldFindByAppointmentIdNotFound() {
        // Buscar un registro médico con una cita que no existe
        Optional<MedicalRecord> foundMedicalRecord = medicalRecordRepository.findByAppointmentId(9999L);

        assertFalse(foundMedicalRecord.isPresent());
    }

    // Test para verificar que el método findByPatientId devuelve una lista vacía si no se encuentran registros médicos para un paciente.
    @Test
    void shouldFindByPatientIdEmpty() {
        // Buscar historial médico por paciente con id que no existe
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByPatientId(9999L);

        assertTrue(medicalRecords.isEmpty());
    }
}