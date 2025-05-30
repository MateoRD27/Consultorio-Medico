package edu.unimag.consultoriomedico.mapper;

import edu.unimag.consultoriomedico.dto.ConsultRoomDTO;
import edu.unimag.consultoriomedico.entity.ConsultRoom;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ConsultRoomMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "roomNumber", target = "roomNumber"),
            @Mapping(source = "floor", target = "floor"),
            @Mapping(source = "description", target = "description")
    })
    ConsultRoomDTO toDTO(ConsultRoom consultRoom);

    @InheritInverseConfiguration
    ConsultRoom toEntity(ConsultRoomDTO dto);
}
