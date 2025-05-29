package edu.unimag.consultoriomedico.service;

import edu.unimag.consultoriomedico.dto.ConsultRoomDTO;
import edu.unimag.consultoriomedico.entity.ConsultRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConsultRoomService {
   //obtener todas las salas
    List<ConsultRoomDTO> getAllConsultRooms();
    //obtener una sala por su id
    ConsultRoomDTO getConsultRoomById(Long id);
    //crear una sala
    ConsultRoomDTO createConsultRoom(ConsultRoomDTO consultRoomDto);
    //actualizar una sala
    ConsultRoomDTO updateConsultRoom(Long id, ConsultRoomDTO consultRoomDto);
    //eliminar una sala
    void deleteConsultRoom(Long id);
}
