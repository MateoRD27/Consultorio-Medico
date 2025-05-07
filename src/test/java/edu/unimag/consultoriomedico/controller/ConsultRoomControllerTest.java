package edu.unimag.consultoriomedico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unimag.consultoriomedico.dto.ConsultRoomDTO;
import edu.unimag.consultoriomedico.service.ConsultRoomService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultRoomController.class)
public class ConsultRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsultRoomService consultRoomService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateConsultRoom() throws Exception {
        ConsultRoomDTO inputDto = new ConsultRoomDTO(null, "Room A", 101, 1, "First floor room");
        ConsultRoomDTO savedDto = new ConsultRoomDTO(1L, "Room A", 101, 1, "First floor room");

        Mockito.when(consultRoomService.createConsultRoom(any(ConsultRoomDTO.class))).thenReturn(savedDto);

        mockMvc.perform(post("/api/consult-rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Room A")))
                .andExpect(jsonPath("$.roomNumber", is(101)))
                .andExpect(jsonPath("$.floor", is(1)))
                .andExpect(jsonPath("$.description", is("First floor room")));
    }

    @Test
    void testGetAllConsultRooms() throws Exception {
        List<ConsultRoomDTO> consultRooms = Arrays.asList(
                new ConsultRoomDTO(1L, "Room A", 101, 1, "First floor room"),
                new ConsultRoomDTO(2L, "Room B", 102, 1, "Second room")
        );

        Mockito.when(consultRoomService.getAllConsultRooms()).thenReturn(consultRooms);

        mockMvc.perform(get("/api/consult-rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    void testGetConsultRoomById() throws Exception {
        ConsultRoomDTO room = new ConsultRoomDTO(1L, "Room A", 101, 1, "First floor room");

        Mockito.when(consultRoomService.getConsultRoomById(1L)).thenReturn(room);

        mockMvc.perform(get("/api/consult-rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Room A")));
    }

    @Test
    void testUpdateConsultRoom() throws Exception {
        ConsultRoomDTO inputDto = new ConsultRoomDTO(null, "Room Updated", 101, 1, "Updated description");
        ConsultRoomDTO updatedDto = new ConsultRoomDTO(1L, "Room Updated", 101, 1, "Updated description");

        Mockito.when(consultRoomService.updateConsultRoom(eq(1L), any(ConsultRoomDTO.class))).thenReturn(updatedDto);

        mockMvc.perform(put("/api/consult-rooms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Room Updated")))
                .andExpect(jsonPath("$.description", is("Updated description")));
    }

    @Test
    void testDeleteConsultRoom() throws Exception {
        Mockito.doNothing().when(consultRoomService).deleteConsultRoom(1L);

        mockMvc.perform(delete("/api/consult-rooms/1"))
                .andExpect(status().isNoContent());
    }
}
