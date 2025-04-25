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



@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    AppointmentRepository appointmentRepository;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    ConsultRoomRepository consultRoomRepository;
    AppointmentMapper appointmentMapper;



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

        //crear la cita
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setConsultRoom(consultRoom);
        appointment.setStatus(appointmentDTO.getStatus());
        appointment.setStartTime(appointmentDTO.getStartTime());
        appointment.setEndTime(appointmentDTO.getEndTime());
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

    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with ID: " + id);
        }
        appointmentRepository.deleteById(id);
    }
}
