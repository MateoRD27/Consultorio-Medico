package edu.unimag.consultoriomedico.controller;

import edu.unimag.consultoriomedico.dto.MedicalRecordDTO;
import edu.unimag.consultoriomedico.service.MedicalRecordsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor

public class MedicalRecordsController {
    private final MedicalRecordsService medicalRecordsService;

    public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords() {
        return ResponseEntity.ok(medicalRecordsService.getAllMedicalRecords());
    }

    public ResponseEntity<MedicalRecordDTO> getMedicalRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordsService.getMedicalRecordById(id));
    }

    public ResponseEntity<List<MedicalRecordDTO>> getMedicalRecordsByPatientId(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordsService.getMedicalRecordsByPatientId(id));
    }

    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(@Valid @RequestBody MedicalRecordDTO medicalRecordDTO) {
        return ResponseEntity.status( HttpStatus.CREATED).body(medicalRecordsService.createMedicalRecord(medicalRecordDTO));
    }
    //delete medical records
    @DeleteMapping
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordsService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
}
