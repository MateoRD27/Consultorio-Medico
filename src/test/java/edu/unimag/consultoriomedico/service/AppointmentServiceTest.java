package edu.unimag.consultoriomedico.service;

import edu.unimag.consultoriomedico.dto.AppointmentDTO;
import edu.unimag.consultoriomedico.entity.*;
import edu.unimag.consultoriomedico.enums.Status;
import edu.unimag.consultoriomedico.mapper.AppointmentMapper;
import edu.unimag.consultoriomedico.repository.AppointmentRepository;
import edu.unimag.consultoriomedico.repository.ConsultRoomRepository;
import edu.unimag.consultoriomedico.repository.DoctorRepository;
import edu.unimag.consultoriomedico.repository.PatientRepository;
import edu.unimag.consultoriomedico.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ConsultRoomRepository consultRoomRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private Appointment appointment;
    private AppointmentDTO appointmentDTO;
    private Doctor doctor;
    private Patient patient;
    private ConsultRoom consultRoom;

    @BeforeEach
    void setUp() {
        LocalTime from = LocalTime.of(9, 0);
        LocalTime to = LocalTime.of(17, 0);
        LocalDateTime start = LocalDateTime.now().plusDays(1).withHour(10);
        LocalDateTime end = start.plusHours(1);

        doctor = Doctor.builder()
                .id(1L)
                .fullName("Dr. Strange")
                .identificationNumber(12345L)
                .specialty("Magic")
                .email("drstrange@example.com")
                .availableFrom(from)
                .availableTo(to)
                .build();

        patient = Patient.builder()
                .id(1L)
                .fullName("John Doe")
                .identificationNumber(54321L)
                .email("johndoe@example.com")
                .build();

        consultRoom = ConsultRoom.builder()
                .id(1L)
                .name("Room A")
                .roomNumber(101)
                .floor(1)
                .description("General consultation")
                .build();

        appointment = Appointment.builder()
                .id(1L)
                .doctor(doctor)
                .patient(patient)
                .consultRoom(consultRoom)
                .startTime(start)
                .endTime(end)
                .status(Status.SCHEDULED)
                .build();

        appointmentDTO = AppointmentDTO.builder()
                .id(1L)
                .doctorId(doctor.getId())
                .patientId(patient.getId())
                .consultRoomId(consultRoom.getId())
                .startTime(start)
                .endTime(end)
                .status(appointment.getStatus())
                .build();
    }

    @Test
    void shouldCreateAppointment_success() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(consultRoomRepository.findById(1L)).thenReturn(Optional.of(consultRoom));

        when(appointmentRepository.existsByDoctorAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(any(), any(), any()))
                .thenReturn(false);
        when(appointmentRepository.existsByConsultRoomAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(any(), any(), any()))
                .thenReturn(false);
        when(appointmentRepository.findConflictsAppointment(anyLong(), anyLong(), any(), any()))
                .thenReturn(Collections.emptyList());

        when(appointmentMapper.toEntity(any(AppointmentDTO.class))).thenReturn(appointment);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(appointmentMapper.toDTO(any(Appointment.class))).thenReturn(appointmentDTO);

        AppointmentDTO result = appointmentService.createAppointment(appointmentDTO);

        assertNotNull(result);
        assertEquals(Status.SCHEDULED, result.getStatus());
    }

    @Test
    void shouldGetAllAppointments() {
        List<Appointment> appointments = List.of(appointment);
        when(appointmentRepository.findAll()).thenReturn(appointments);
        when(appointmentMapper.toDTO(any(Appointment.class))).thenReturn(appointmentDTO);

        Iterable<AppointmentDTO> result = appointmentService.getAllAppointments();

        assertNotNull(result);
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void shouldGetAppointmentById() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentMapper.toDTO(any(Appointment.class))).thenReturn(appointmentDTO);

        AppointmentDTO result = appointmentService.getAppointmentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateAppointment() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(appointment.getDoctor()));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(appointment.getPatient()));
        when(consultRoomRepository.findById(1L)).thenReturn(Optional.of(appointment.getConsultRoom()));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(appointmentMapper.toDTO(any(Appointment.class))).thenReturn(appointmentDTO);

        AppointmentDTO result = appointmentService.updateAppointment(1L, appointmentDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(appointmentRepository, times(1)).findById(1L);
        verify(doctorRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).findById(1L);
        verify(consultRoomRepository, times(1)).findById(1L);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }


    @Test
    void shouldDeleteAppointment() {
        when(appointmentRepository.existsById(1L)).thenReturn(true);

        appointmentService.deleteAppointment(1L);

        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}
