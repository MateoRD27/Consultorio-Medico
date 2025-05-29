package edu.unimag.consultoriomedico.service;

import edu.unimag.consultoriomedico.dto.AppointmentDTO;
import edu.unimag.consultoriomedico.entity.Appointment;
import org.springframework.stereotype.Service;

@Service
public interface AppointmentService {
    // crear una cita nueva
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);

    // obtener todas las citas
    Iterable<AppointmentDTO> getAllAppointments();

    // obtener una cita por id
    AppointmentDTO getAppointmentById(Long id);

    // actualizar una cita
    AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO);

    // eliminar una cita
    void deleteAppointment(Long id);
}
