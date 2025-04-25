package edu.unimag.consultoriomedico.service;

import edu.unimag.consultoriomedico.dto.ConsultRoomDTO;
import edu.unimag.consultoriomedico.entity.ConsultRoom;
import edu.unimag.consultoriomedico.exception.NoDataFoundException;
import edu.unimag.consultoriomedico.mapper.ConsultRoomMapper;
import edu.unimag.consultoriomedico.repository.ConsultRoomRepository;
import edu.unimag.consultoriomedico.service.impl.ConsultRoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultRoomServiceTest {

    @Mock
    private ConsultRoomRepository consultRoomRepository;

    @Mock
    private ConsultRoomMapper consultRoomMapper;

    @InjectMocks
    private ConsultRoomServiceImpl consultRoomService;

    private ConsultRoom consultRoom;
    private ConsultRoomDTO consultRoomDTO;

    @BeforeEach
    void setUp() {
        // Configuración inicial de objetos para los tests
        consultRoom = ConsultRoom.builder()
                .id(1L)
                .name("Room 101")
                .roomNumber(101)
                .floor(1)
                .description("General Consultation Room")
                .build();

        consultRoomDTO = ConsultRoomDTO.builder()
                .id(1L)
                .name("Room 101")
                .roomNumber(101)
                .floor(1)
                .description("General Consultation Room")
                .build();
    }



    // Test para verificar que se devuelven los DTOs de consultorios
    @Test
    void shouldGetALLConsultRoom() {
        when(consultRoomRepository.findAll()).thenReturn(List.of(consultRoom));
        when(consultRoomMapper.toDTO(consultRoom)).thenReturn(consultRoomDTO);

        List<ConsultRoomDTO> result = consultRoomService.getAllConsultRooms();

        assertEquals(1, result.size());
        assertEquals("Room 101", result.get(0).getName());
    }

    // Test para verificar que se retorna un consultorio cuando se encuentra por ID
    @Test
    void shouldGetConsultRoomFoundById() {
        when(consultRoomRepository.findById(1L)).thenReturn(Optional.of(consultRoom));
        when(consultRoomMapper.toDTO(consultRoom)).thenReturn(consultRoomDTO);

        ConsultRoomDTO result = consultRoomService.getConsultRoomById(1L);

        assertNotNull(result);
        assertEquals("Room 101", result.getName());
    }


    // Test para verificar que se crea un nuevo consultorio cuando no existe
    @Test
    void shouldCreatedConsultRoom() {
        when(consultRoomRepository.findByRoomNumber(101)).thenReturn(Optional.empty());
        when(consultRoomMapper.toEntity(consultRoomDTO)).thenReturn(consultRoom);
        when(consultRoomRepository.save(consultRoom)).thenReturn(consultRoom);
        when(consultRoomMapper.toDTO(consultRoom)).thenReturn(consultRoomDTO);

        ConsultRoomDTO result = consultRoomService.createConsultRoom(consultRoomDTO);

        assertNotNull(result);
        assertEquals("Room 101", result.getName());
    }



    // Test para verificar que se actualiza correctamente un consultorio
    @Test
    void shouldUpdatedConsultRoom() {
        // Datos de prueba para la actualización
        ConsultRoomDTO updatedDTO = ConsultRoomDTO.builder()
                .id(1L)
                .name("Room 101")
                .roomNumber(101)
                .floor(1)
                .description("Updated Room")
                .build();

        ConsultRoom existingRoom = ConsultRoom.builder()
                .id(1L)
                .name("Room 101")
                .roomNumber(101)
                .floor(1)
                .description("General Consultation Room")
                .build();

        // Mocking de los métodos necesarios
        when(consultRoomRepository.findById(1L)).thenReturn(Optional.of(existingRoom));
        when(consultRoomRepository.save(existingRoom)).thenReturn(existingRoom);
        when(consultRoomMapper.toDTO(existingRoom)).thenReturn(updatedDTO);

        // Ejecutar el servicio
        ConsultRoomDTO result = consultRoomService.updateConsultRoom(1L, updatedDTO);

        // Verificar resultados
        assertNotNull(result);
        assertEquals("Room 101", result.getName());
        assertEquals("Updated Room", result.getDescription());
    }

    // Test para verificar que se lanza una excepción si no se encuentra un consultorio para eliminar
    @Test
    void shouldThrowNoDataFoundExceptionWhenConsultRoomNotFoundToDelete() {
        when(consultRoomRepository.existsById(1L)).thenReturn(false);

        NoDataFoundException exception = assertThrows(NoDataFoundException.class, () -> {
            consultRoomService.deleteConsultRoom(1L);
        });
        assertEquals("Consult room not found with ID: " + 1L, exception.getMessage());
    }

    // Test para verificar que se elimina un consultorio correctamente
    @Test
    void shouldDeleteConsultRoom() {
        when(consultRoomRepository.existsById(1L)).thenReturn(true);

        consultRoomService.deleteConsultRoom(1L);

        verify(consultRoomRepository, times(1)).deleteById(1L);
    }

    // Test para verificar que se lanza una excepción si se intenta crear un consultorio que ya existe
    @Test
    void shouldThrowNoDataFoundExceptionWhenConsultRoomAlreadyExists() {
        when(consultRoomRepository.findByRoomNumber(101)).thenReturn(Optional.of(consultRoom));

        NoDataFoundException exception = assertThrows(NoDataFoundException.class, () -> {
            consultRoomService.createConsultRoom(consultRoomDTO);
        });
        assertEquals("Consult room already exists with room number: 101", exception.getMessage());
    }

    // Test para verificar que se lanza una excepción cuando no se encuentran consultorios
    @Test
    void shouldThrowNoDataFoundExceptionWhenNoConsultRoomsFound() {
        when(consultRoomRepository.findAll()).thenReturn(java.util.Collections.emptyList());

        NoDataFoundException exception = assertThrows(NoDataFoundException.class, () -> {
            consultRoomService.getAllConsultRooms();
        });
        assertEquals("No consult rooms found", exception.getMessage());
    }

    // Test para verificar que se lanza una excepción si no se encuentra un consultorio para actualizar
    @Test
    void shouldThrowNoDataFoundExceptionWhenConsultRoomNotFoundToUpdate() {
        when(consultRoomRepository.findById(1L)).thenReturn(Optional.empty());

        NoDataFoundException exception = assertThrows(NoDataFoundException.class, () -> {
            consultRoomService.updateConsultRoom(1L, consultRoomDTO);
        });
        assertEquals("Consult room not found with ID: " + 1L, exception.getMessage());
    }

    // Test para verificar que se lanza una excepción cuando no se encuentra un consultorio por ID
    @Test
    void shouldThrowNoDataFoundExceptionWhenConsultRoomNotFoundById() {
        when(consultRoomRepository.findById(1L)).thenReturn(Optional.empty());

        NoDataFoundException exception = assertThrows(NoDataFoundException.class, () -> {
            consultRoomService.getConsultRoomById(1L);
        });
        assertEquals("Consult room not found with ID: " + 1L, exception.getMessage());
    }
}
