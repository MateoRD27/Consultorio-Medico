package edu.unimag.consultoriomedico.controller;

import edu.unimag.consultoriomedico.dto.ConsultRoomDTO;
import edu.unimag.consultoriomedico.repository.ConsultRoomRepository;
import edu.unimag.consultoriomedico.service.ConsultRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/consult-rooms")
@RequiredArgsConstructor
public class ConsultRoomController
{
    ConsultRoomService consultRoomService;

    @GetMapping
    public ResponseEntity<List<ConsultRoomDTO>> getAllConsultRooms() {
        return ResponseEntity.ok(consultRoomService.getAllConsultRooms());
    }

    @GetMapping
    public ResponseEntity<ConsultRoomDTO> getConsultRoomById(Long id) {
        return ResponseEntity.ok(consultRoomService.getConsultRoomById(id));
    }

    @PostMapping
    public ResponseEntity<ConsultRoomDTO> createConsultRoom(ConsultRoomDTO consultRoomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultRoomService.createConsultRoom(consultRoomDto));
    }

    @PutMapping
    public ResponseEntity<ConsultRoomDTO> updateConsultRoom(@PathVariable Long id, @Valid @RequestBody ConsultRoomDTO consultRoomDto) {
        return ResponseEntity.ok(consultRoomService.updateConsultRoom(id, consultRoomDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultRoom(@PathVariable Long id) {
        consultRoomService.deleteConsultRoom(id);
        return ResponseEntity.noContent().build();
    }

}
