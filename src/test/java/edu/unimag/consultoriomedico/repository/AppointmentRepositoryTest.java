package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppointmentRepositoryTest {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultRoomRepository consultRoomRepository;

    @Autowired
    private PatientRepository patientRepository;

    Doctor doctor;
    Patient patient;
    ConsultRoom consultRoom;


    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
        patientRepository.deleteAll();
        consultRoomRepository.deleteAll();
        doctorRepository.deleteAll();
        doctor = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Ramos")
                .identificationNumber(123456789L)
                .specialty("Medicina General")
                .email("doctor.ramos@example.com")
                .availableFrom(LocalTime.of(8, 0))
                .availableTo(LocalTime.of(17, 0))
                .build());


        patient = patientRepository.save(Patient.builder()
                .fullName("Jeferson Flórez")
                .identificationNumber(987654321L)
                .email("jeferson@email.com")
                .phoneNumber("1234567890")
                .build());


        consultRoom = consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 1")
                .roomNumber(101)
                .floor(2)
                .description("Consultation Room 1")
                .build());
    }



    @Test
    void shouldDetectConflictingAppointments() {
        LocalDateTime start = LocalDateTime.now().plusDays(1).withHour(10);
        LocalDateTime end = start.plusHours(1);

        appointmentRepository.save(Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .consultRoom(consultRoom)
                .startTime(start)
                .endTime(end)
                .status(Status.SCHEDULED)
                .build());

        List<Appointment> conflicts = appointmentRepository.findConflictsAppointment(
                doctor.getId(), consultRoom.getId(), start.plusMinutes(30), end.plusHours(1));

        assertFalse(conflicts.isEmpty());
    }

    @Test
    void shouldSaveAppointmentWithoutConflict() {
        LocalDateTime start = LocalDateTime.now().plusDays(2).withHour(14);
        LocalDateTime end = start.plusHours(1);

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .consultRoom(consultRoom)
                .startTime(start)
                .endTime(end)
                .status(Status.SCHEDULED)
                .build();

        Appointment saved = appointmentRepository.save(appointment);

        assertNotNull(saved.getId());
        assertEquals(Status.SCHEDULED, saved.getStatus());
    }

    @Test
    void shouldFindStatusAppointment() {
        LocalDateTime start = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = start.plusHours(1);

        Appointment completed = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .consultRoom(consultRoom)
                .startTime(start)
                .endTime(end)
                .status(Status.COMPLETED)
                .build();


        Appointment saved = appointmentRepository.save(completed);

        Status result = appointmentRepository.findStatusAppointment(saved.getId());
        assertEquals(Status.COMPLETED, result);
    }
}