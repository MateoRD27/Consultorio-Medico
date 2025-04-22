package edu.unimag.consultoriomedico.mapper;

import edu.unimag.consultoriomedico.dto.DoctorDTO;
import edu.unimag.consultoriomedico.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTO toDto(Doctor doctor);
    Doctor toEntity(DoctorDTO dto);
}
