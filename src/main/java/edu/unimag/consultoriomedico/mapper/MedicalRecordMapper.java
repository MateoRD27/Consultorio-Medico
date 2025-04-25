package edu.unimag.consultoriomedico.mapper;

import edu.unimag.consultoriomedico.dto.MedicalRecordDTO;
import edu.unimag.consultoriomedico.entity.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {
    @Mapping(source = "appointment.id", target = "appointmentId")
    @Mapping(source = "patient.id", target = "patientId")

    MedicalRecordDTO toDTO(MedicalRecord medicalRecord);

    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "patient", ignore = true)
    MedicalRecord toEntity(MedicalRecordDTO medicalRecordDTO);
}
