package edu.unimag.consultoriomedico.mapper;

import edu.unimag.consultoriomedico.dto.ConsultRoomDTO;
import edu.unimag.consultoriomedico.entity.ConsultRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsultRoomMapper {
    ConsultRoomDTO toDTO(ConsultRoom consultRoom);
    ConsultRoom toEntity(ConsultRoomDTO consultRoomDTO);
}
