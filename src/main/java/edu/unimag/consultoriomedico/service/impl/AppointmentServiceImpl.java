package edu.unimag.consultoriomedico.service.impl;

import edu.unimag.consultoriomedico.dto.AppointmentDTO;
import edu.unimag.consultoriomedico.entity.Appointment;
import edu.unimag.consultoriomedico.entity.ConsultRoom;
import edu.unimag.consultoriomedico.entity.Doctor;
import edu.unimag.consultoriomedico.entity.Patient;
import edu.unimag.consultoriomedico.exception.ConflictException;
import edu.unimag.consultoriomedico.exception.ResourceNotFoundException;
import edu.unimag.consultoriomedico.mapper.AppointmentMapper;
import edu.unimag.consultoriomedico.repository.AppointmentRepository;
import edu.unimag.consultoriomedico.repository.ConsultRoomRepository;
import edu.unimag.consultoriomedico.repository.DoctorRepository;
import edu.unimag.consultoriomedico.repository.PatientRepository;
import edu.unimag.consultoriomedico.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ConsultRoomRepository consultRoomRepository;
    private final AppointmentMapper appointmentMapper;



    //crear una cita
    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        Doctor doctor = this.doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + appointmentDTO.getDoctorId()));



        Patient patient = this.patientRepository.findById(appointmentDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + appointmentDTO.getPatientId()));
        //verificar si el consultorio existe
        ConsultRoom consultRoom = this.consultRoomRepository.findById(appointmentDTO.getConsultRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Consult room not found with ID: " + appointmentDTO.getConsultRoomId()));


        //verificar si el doctor ya tiene una cita en la misma fecha y hora en el intervalo de tiempo
        if (this.appointmentRepository.existsByDoctorAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(doctor, appointmentDTO.getEndTime(), appointmentDTO.getStartTime())) {
            throw new ConflictException( "Doctor", "already has an appointment in the same date and time");
        }
        //verificar si el consultorio ya tiene una cita en la misma fecha y hora en el intervalo de tiempo
        if (this.appointmentRepository.existsByConsultRoomAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(consultRoom, appointmentDTO.getEndTime(), appointmentDTO.getStartTime())) {
            throw new ConflictException( "ConsultRoom", "already has an appointment in the same date and time");
        }

        // verificar que no hayan citas en conflicto
        if (!this.appointmentRepository.findConflictsAppointment(doctor.getId(), consultRoom.getId(), appointmentDTO.getStartTime(), appointmentDTO.getEndTime()).isEmpty()) {
            throw new ConflictException( "DoctorOrRoom", "already has an appointment in the same date and time");
        }

        //verificar que el doctor trabaje en el horario de la cita, el horario de la cita debe estar dentro del intervalo de horario de trabajo del doctor
        if (appointmentDTO.getStartTime().toLocalTime().isBefore(doctor.getAvailableFrom()) || appointmentDTO.getEndTime().toLocalTime().isAfter(doctor.getAvailableTo())) {
            throw new ConflictException( "Doctor", "is not available in the selected time");
        }

        //crear la cita
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment.setPatient(patient);
        appointment.setConsultRoom(consultRoom);
        appointment.setStatus(appointmentDTO.getStatus());
        appointment.setStartTime(appointmentDTO.getStartTime());
        appointment.setEndTime(appointmentDTO.getEndTime());
        appointment.setDoctor(doctor);
        //guardar la cita
        return appointmentMapper.toDTO(appointmentRepository.save(appointment));

    }

    @Override
    public Iterable<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointmentMapper::toDTO)
                .toList();
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(appointmentMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + id));
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        //obtener la cita por id y verfiar que exista
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + id));

        //obtener el doctor y verificar que exista
        Doctor doctor = doctorRepository.findById( appointmentDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + appointmentDTO.getDoctorId()));
        //obtener el paciente y verificar que exista
        Patient patient = patientRepository.findById( appointmentDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + appointmentDTO.getPatientId()));
        //obtener el consultorio y verificar que exista
        ConsultRoom consultRoom = consultRoomRepository.findById( appointmentDTO.getConsultRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Consult room not found with ID: " + appointmentDTO.getConsultRoomId()));

        //verificar si la cita tiene coflicto con otra
        List<Appointment> conflicts = appointmentRepository.findConflictsAppointment(doctor.getId(), consultRoom.getId(), appointmentDTO.getStartTime(), appointmentDTO.getEndTime())
                .stream()
                .filter(a -> !a.getId().equals(id)) // Excluir la cita actual
                .toList();

        if (!conflicts.isEmpty()) {
            throw new ConflictException( "DoctorOrRoom", "already has an appointment in the same date and time");
        }

        //verificar que el doctor trabaje en el horario de la cita, el horario de la cita debe estar dentro del intervalo de horario de trabajo del doctor
        if (appointmentDTO.getStartTime().toLocalTime().isBefore(doctor.getAvailableFrom()) || appointmentDTO.getEndTime().toLocalTime().isAfter(doctor.getAvailableTo())) {
            throw new ConflictException( "Doctor", "is not available in the selected time");
        }


        existingAppointment.setDoctor(doctor);
        existingAppointment.setPatient(patient);
        existingAppointment.setConsultRoom(consultRoom);
        existingAppointment.setStatus(appointmentDTO.getStatus());
        existingAppointment.setStartTime(appointmentDTO.getStartTime());
        existingAppointment.setEndTime(appointmentDTO.getEndTime());
        //guardar la cita

        return appointmentMapper.toDTO(appointmentRepository.save(existingAppointment));
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with ID: " + id);
        }
        appointmentRepository.deleteById(id);
    }
}
