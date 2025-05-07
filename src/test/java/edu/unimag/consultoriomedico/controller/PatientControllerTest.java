package edu.unimag.consultoriomedico.controller;

import edu.unimag.consultoriomedico.dto.PatientDTO;
import edu.unimag.consultoriomedico.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private PatientDTO patientDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientDTO = PatientDTO.builder()
                .id(1L)
                .fullName("John Doe")
                .identificationNumber(123456789L)
                .email("johndoe@example.com")
                .phoneNumber("1234567890")
                .build();
    }

    @Test
    void testCreatePatient() {
        when(patientService.createPatient(any(PatientDTO.class))).thenReturn(patientDTO);

        ResponseEntity<PatientDTO> response = patientController.createPatient(patientDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(patientDTO, response.getBody());
        verify(patientService, times(1)).createPatient(any(PatientDTO.class));
    }

    @Test
    void testGetAllPatients() {
        List<PatientDTO> patients = Arrays.asList(patientDTO);
        when(patientService.getAllPatients()).thenReturn(patients);

        ResponseEntity<List<PatientDTO>> response = patientController.getAllPatients();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    void testGetPatientById() {
        when(patientService.getPatientById(1L)).thenReturn(patientDTO);

        ResponseEntity<PatientDTO> response = patientController.getPatientById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(patientDTO, response.getBody());
        verify(patientService, times(1)).getPatientById(1L);
    }

    @Test
    void testUpdatePatient() {
        when(patientService.updatePatient(eq(1L), any(PatientDTO.class))).thenReturn(patientDTO);

        ResponseEntity<PatientDTO> response = patientController.updatePatient(1L, patientDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(patientDTO, response.getBody());
        verify(patientService, times(1)).updatePatient(eq(1L), any(PatientDTO.class));
    }

    @Test
    void testDeletePatient() {
        doNothing().when(patientService).deletePatient(1L);

        ResponseEntity<Void> response = patientController.deletePatient(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(patientService, times(1)).deletePatient(1L);
    }
}