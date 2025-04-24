package edu.unimag.consultoriomedico.service.impl;

import edu.unimag.consultoriomedico.dto.ConsultRoomDTO;
import edu.unimag.consultoriomedico.entity.ConsultRoom;
import edu.unimag.consultoriomedico.exception.NoDataFoundException;
import edu.unimag.consultoriomedico.mapper.ConsultRoomMapper;
import edu.unimag.consultoriomedico.repository.ConsultRoomRepository;
import edu.unimag.consultoriomedico.service.ConsultRoomService;

import java.util.List;
import java.util.stream.Collectors;

public class ConsultRoomServiceImpl implements ConsultRoomService {

    ConsultRoomRepository consultRoomRepository;
    ConsultRoomMapper consultRoomMapper;

    // obtener todos los consultorios
    @Override
    public List<ConsultRoomDTO> getAllConsultRooms() {
        List<ConsultRoom> consultRooms = consultRoomRepository.findAll();
        if (consultRooms.isEmpty()) {
            throw new NoDataFoundException("No consult rooms found");
        }
        return consultRooms.stream()
                .map(consultRoomMapper::toDTO)
                .collect(Collectors.toList());
    }

    // obtener consultorio por id

    @Override
    public ConsultRoomDTO getConsultRoomById(Long id) {
        return consultRoomRepository.findById(id)
                .map(consultRoomMapper::toDTO)
                .orElseThrow(() -> new NoDataFoundException("Consult room not found with ID: " + id));
    }

    //crear un cosultorio
    @Override
    public ConsultRoomDTO createConsultRoom(ConsultRoomDTO consultRoomDto) {
        //verificar si el consultorio ya existe
        if (consultRoomRepository.findByRoomNumber(consultRoomDto.getRoomNumber()).isPresent()) {
            throw new NoDataFoundException("Consult room already exists with room number: " + consultRoomDto.getRoomNumber());
        }

        //mapear el dto a la entidad
       ConsultRoom consultRoom = consultRoomMapper.toEntity(consultRoomDto);
        //guardar y retornar el consultorio
        return consultRoomMapper.toDTO(consultRoomRepository.save(consultRoom));

    }

    @Override
    public ConsultRoomDTO updateConsultRoom(Long id, ConsultRoomDTO consultRoomDto) {
        //verificar si el consultorio ya existe
        ConsultRoom existing = consultRoomRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("Consult room not found with ID: " + id));

        //actualizar los datos del consultorio
        existing.setRoomNumber(consultRoomDto.getRoomNumber());
        existing.setName(consultRoomDto.getName());
        existing.setFloor(consultRoomDto.getFloor());
        existing.setDescription(consultRoomDto.getDescription());
        existing.setFloor(consultRoomDto.getFloor());

        return consultRoomMapper.toDTO(consultRoomRepository.save(existing));
    }

    @Override
    public void deleteConsultRoom(Long id) {

        if (!consultRoomRepository.existsById(id)) {
            throw new NoDataFoundException("Consult room not found with ID: " + id);
        }
        consultRoomRepository.deleteById(id);

    }
}
