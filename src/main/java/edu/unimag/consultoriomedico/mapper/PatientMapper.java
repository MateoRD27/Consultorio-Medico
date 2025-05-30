package edu.unimag.consultoriomedico.mapper;

import edu.unimag.consultoriomedico.dto.PatientDTO;
import edu.unimag.consultoriomedico.entity.Patient;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "identificationNumber", target = "identificationNumber")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    PatientDTO toDTO(Patient patient);

    @InheritInverseConfiguration
    Patient toEntity(PatientDTO patientDTO);
}
