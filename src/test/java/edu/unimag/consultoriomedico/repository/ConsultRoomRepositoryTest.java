package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.ConsultRoom;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConsultRoomRepositoryTest {

    @Autowired
    private ConsultRoomRepository consultRoomRepository;

    ConsultRoom room1, room2;

    @BeforeEach
    void setUp() {
        consultRoomRepository.deleteAll();

        room1 = consultRoomRepository.save(ConsultRoom.builder()
                .name("Sala A")
                .roomNumber(101)
                .floor(1)
                .description("Sala de consulta general")
                .build());

        room2 = consultRoomRepository.save(ConsultRoom.builder()
                .name("Sala B")
                .roomNumber(102)
                .floor(2)
                .description("Sala de consulta especializada")
                .build());
    }

    @Test
    void shouldSaveAndFindConsultRoom() {
        ConsultRoom newRoom = consultRoomRepository.save(ConsultRoom.builder()
                .name("Sala C")
                .roomNumber(103)
                .floor(3)
                .description("Sala pediátrica")
                .build());

        Optional<ConsultRoom> result = consultRoomRepository.findById(newRoom.getId());

        assertTrue(result.isPresent());
        assertEquals("Sala C", result.get().getName());
    }

    @Test
    void shouldFindAllConsultRooms() {
        List<ConsultRoom> rooms = consultRoomRepository.findAll();
        assertEquals(2, rooms.size());
    }

    @Test
    void shouldUpdateConsultRoom() {
        room1.setDescription("Sala de consulta actualizada");
        ConsultRoom updated = consultRoomRepository.save(room1);

        assertEquals("Sala de consulta actualizada", updated.getDescription());
    }

    @Test
    void shouldDeleteConsultRoom() {
        Long id = room2.getId();
        consultRoomRepository.deleteById(id);

        assertFalse(consultRoomRepository.findById(id).isPresent());
    }
}