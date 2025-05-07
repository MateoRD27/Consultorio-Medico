package edu.unimag.consultoriomedico.controller;

import edu.unimag.consultoriomedico.dto.DoctorDTO;
import edu.unimag.consultoriomedico.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    private DoctorDTO doctorDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doctorDTO = DoctorDTO.builder()
                .id(1L)
                .fullName("Dr. John Doe")
                .identificationNumber(123456789L)
                .specialty("Cardiology")
                .email("john.doe@example.com")
                .availableFrom(LocalTime.of(9, 0))
                .availableTo(LocalTime.of(17, 0))
                .build();
    }

    @Test
    void testCreateDoctor() {
        when(doctorService.createDoctor(any(DoctorDTO.class))).thenReturn(doctorDTO);

        ResponseEntity<DoctorDTO> response = doctorController.createDoctor(doctorDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(doctorDTO, response.getBody());
        verify(doctorService, times(1)).createDoctor(any(DoctorDTO.class));
    }

    @Test
    void testGetAllDoctors() {
        List<DoctorDTO> doctorList = Arrays.asList(doctorDTO);
        when(doctorService.getAllDoctors()).thenReturn(doctorList);

        ResponseEntity<List<DoctorDTO>> response = doctorController.getAllDoctors();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(doctorService, times(1)).getAllDoctors();
    }

    @Test
    void testGetDoctorById() {
        when(doctorService.getDoctorById(1L)).thenReturn(doctorDTO);

        ResponseEntity<DoctorDTO> response = doctorController.getDoctorById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(doctorDTO, response.getBody());
        verify(doctorService, times(1)).getDoctorById(1L);
    }

    @Test
    void testGetDoctorsBySpecialty() {
        List<DoctorDTO> doctorList = Arrays.asList(doctorDTO);
        when(doctorService.getDoctorsBySpecialty("Cardiology")).thenReturn(doctorList);

        ResponseEntity<List<DoctorDTO>> response = doctorController.getDoctorsBySpecialty("Cardiology");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(doctorService, times(1)).getDoctorsBySpecialty("Cardiology");
    }

    @Test
    void testUpdateDoctor() {
        DoctorDTO updatedDoctor = DoctorDTO.builder()
                .id(1L)
                .fullName("Dr. John Updated")
                .identificationNumber(123456789L)
                .specialty("Cardiology")
                .email("john.updated@example.com")
                .availableFrom(LocalTime.of(10, 0))
                .availableTo(LocalTime.of(18, 0))
                .build();

        when(doctorService.updateDoctor(eq(1L), any(DoctorDTO.class))).thenReturn(updatedDoctor);

        ResponseEntity<DoctorDTO> response = doctorController.updateDoctor(1L, updatedDoctor);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedDoctor, response.getBody());
        verify(doctorService, times(1)).updateDoctor(eq(1L), any(DoctorDTO.class));
    }

    @Test
    void testDeleteDoctor() {
        doNothing().when(doctorService).deleteDoctor(1L);

        ResponseEntity<Void> response = doctorController.deleteDoctor(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(doctorService, times(1)).deleteDoctor(1L);
    }
}
