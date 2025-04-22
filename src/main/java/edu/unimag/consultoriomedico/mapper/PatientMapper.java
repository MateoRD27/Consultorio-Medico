package edu.unimag.consultoriomedico.mapper;

import edu.unimag.consultoriomedico.dto.PatientDTO;
import edu.unimag.consultoriomedico.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO toDTO(Patient patient);
    Patient toEntity(PatientDTO patientDTO);
}
