package edu.unimag.consultoriomedico.service.impl;

import edu.unimag.consultoriomedico.dto.ConsultRoomDTO;
import edu.unimag.consultoriomedico.service.ConsultRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultRoomServiceImpl implements ConsultRoomService {

    @Override
    public List<ConsultRoomDTO> getAllConsultRooms() {
        return List.of();
    }

    @Override
    public ConsultRoomDTO getConsultRoomById(Long id) {
        return null;
    }

    @Override
    public ConsultRoomDTO createConsultRoom(ConsultRoomDTO consultRoomDto) {
        return null;
    }

    @Override
    public ConsultRoomDTO updateConsultRoom(Long id, ConsultRoomDTO consultRoomDto) {
        return null;
    }

    @Override
    public void deleteConsultRoom(Long id) {

    }
}
