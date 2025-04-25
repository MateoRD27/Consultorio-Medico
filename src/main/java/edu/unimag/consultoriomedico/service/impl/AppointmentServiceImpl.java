package edu.unimag.consultoriomedico.service.impl;

import edu.unimag.consultoriomedico.dto.AppointmentDTO;
import edu.unimag.consultoriomedico.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        return null;
    }

    @Override
    public Iterable<AppointmentDTO> getAllAppointments() {
        return null;
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        return null;
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        return null;
    }

    @Override
    public void deleteAppointment(Long id) {

    }
}
