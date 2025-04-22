package edu.unimag.consultoriomedico.mapper;

import edu.unimag.consultoriomedico.dto.AppointmentDTO;
import edu.unimag.consultoriomedico.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "consultRoom.id", target = "consultRoomId")
    AppointmentDTO toDTO(Appointment appointment);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "consultRoom", ignore = true)
    Appointment toEntity(AppointmentDTO appointmentDTO);
}
