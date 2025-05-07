package edu.unimag.consultoriomedico.controller;

import edu.unimag.consultoriomedico.dto.AppointmentDTO;
import edu.unimag.consultoriomedico.entity.Status;
import edu.unimag.consultoriomedico.service.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
@Import(AppointmentControllerTest.MockConfig.class)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppointmentService appointmentService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public AppointmentService appointmentService() {
            return Mockito.mock(AppointmentService.class);
        }
    }

    @Test
    void shouldCreateAppointmentSuccessfully() throws Exception {
        AppointmentDTO dto = AppointmentDTO.builder()
                .id(1L)
                .doctorId(1L)
                .patientId(1L)
                .consultRoomId(1L)
                .startTime(LocalDateTime.now().plusDays(1))  // Validación de fecha futura
                .status(Status.SCHEDULED)
                .build();

        when(appointmentService.createAppointment(any())).thenReturn(dto);

        mockMvc.perform(post("/api/appointments")
                        .with(csrf())  // Token CSRF simulado
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(Status.SCHEDULED));
    }

    @Test
    void shouldReturnAllAppointments() throws Exception {
        AppointmentDTO dto = AppointmentDTO.builder()
                .id(1L)
                .doctorId(1L)
                .patientId(1L)
                .consultRoomId(1L)
                .startTime(LocalDateTime.now().plusDays(1))
                .status(Status.SCHEDULED)
                .build();

        when(appointmentService.getAllAppointments()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void shouldGetAppointmentById() throws Exception {
        AppointmentDTO dto = AppointmentDTO.builder()
                .id(1L)
                .doctorId(1L)
                .patientId(1L)
                .consultRoomId(1L)
                .startTime(LocalDateTime.now().plusDays(1))
                .status(Status.SCHEDULED)
                .build();

        when(appointmentService.getAppointmentById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/appointments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void shouldUpdateAppointmentSuccessfully() throws Exception {
        AppointmentDTO dto = AppointmentDTO.builder()
                .id(1L)
                .doctorId(2L) // Cambio simulado
                .patientId(1L)
                .consultRoomId(1L)
                .startTime(LocalDateTime.now().plusDays(2))  // Fecha futura
                .status(Status.COMPLETED)  // Cambio de estado
                .build();

        when(appointmentService.updateAppointment(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(put("/api/appointments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorId").value(2L));
    }

    @Test
    void shouldDeleteAppointmentSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/appointments/1"))
                .andExpect(status().isNoContent());

        verify(appointmentService).deleteAppointment(1L);
    }

    @Test
    void shouldReturnBadRequestWhenStartTimeIsNotFuture() throws Exception {
        // Simulando fecha pasada
        AppointmentDTO dto = AppointmentDTO.builder()
                .id(1L)
                .doctorId(1L)
                .patientId(1L)
                .consultRoomId(1L)
                .startTime(LocalDateTime.now().minusDays(1))  // Fecha pasada
                .status(Status.SCHEDULED)
                .build();

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Start time must be in the future"));
    }
}
