package edu.unimag.consultoriomedico.mapper;

import edu.unimag.consultoriomedico.dto.DoctorDTO;
import edu.unimag.consultoriomedico.entity.Doctor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "fullName", target = "fullName"),
            @Mapping(source = "identificationNumber", target = "identificationNumber"),
            @Mapping(source = "specialty", target = "specialty"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "availableFrom", target = "availableFrom"),
            @Mapping(source = "availableTo", target = "availableTo")
    })
    DoctorDTO toDTO(Doctor doctor);

    @InheritInverseConfiguration
    Doctor toEntity(DoctorDTO dto);
}
