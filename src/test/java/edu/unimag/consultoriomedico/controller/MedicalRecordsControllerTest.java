package edu.unimag.consultoriomedico.controller;

import edu.unimag.consultoriomedico.dto.MedicalRecordDTO;
import edu.unimag.consultoriomedico.service.MedicalRecordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicalRecordsControllerTest {

    @Mock
    private MedicalRecordsService medicalRecordsService;

    @InjectMocks
    private MedicalRecordsController medicalRecordsController;

    private MedicalRecordDTO medicalRecordDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        medicalRecordDTO = MedicalRecordDTO.builder()
                .id(1L)
                .appointmentId(10L)
                .patientId(100L)
                .diagnosis("Flu")
                .notes("Rest and hydration")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateMedicalRecord() {
        when(medicalRecordsService.createMedicalRecord(any(MedicalRecordDTO.class)))
                .thenReturn(medicalRecordDTO);

        ResponseEntity<MedicalRecordDTO> response = medicalRecordsController.createMedicalRecord(medicalRecordDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(medicalRecordDTO, response.getBody());
        verify(medicalRecordsService, times(1)).createMedicalRecord(any(MedicalRecordDTO.class));
    }

    @Test
    void testGetAllMedicalRecords() {
        List<MedicalRecordDTO> recordList = Arrays.asList(medicalRecordDTO);
        when(medicalRecordsService.getAllMedicalRecords()).thenReturn(recordList);

        ResponseEntity<List<MedicalRecordDTO>> response = medicalRecordsController.getAllMedicalRecords();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(medicalRecordsService, times(1)).getAllMedicalRecords();
    }

    @Test
    void testGetMedicalRecordById() {
        when(medicalRecordsService.getMedicalRecordById(1L)).thenReturn(medicalRecordDTO);

        ResponseEntity<MedicalRecordDTO> response = medicalRecordsController.getMedicalRecordById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(medicalRecordDTO, response.getBody());
        verify(medicalRecordsService, times(1)).getMedicalRecordById(1L);
    }

    @Test
    void testGetMedicalRecordsByPatientId() {
        List<MedicalRecordDTO> recordList = Arrays.asList(medicalRecordDTO);
        when(medicalRecordsService.getMedicalRecordsByPatientId(100L)).thenReturn(recordList);

        ResponseEntity<List<MedicalRecordDTO>> response = medicalRecordsController.getMedicalRecordsByPatientId(100L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(medicalRecordsService, times(1)).getMedicalRecordsByPatientId(100L);
    }

    @Test
    void testDeleteMedicalRecord() {
        doNothing().when(medicalRecordsService).deleteMedicalRecord(1L);

        ResponseEntity<Void> response = medicalRecordsController.deleteMedicalRecord(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(medicalRecordsService, times(1)).deleteMedicalRecord(1L);
    }
}
