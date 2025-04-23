package edu.unimag.consultoriomedico.service.impl;

import edu.unimag.consultoriomedico.dto.MedicalRecordDTO;
import edu.unimag.consultoriomedico.service.MedicalRecordsService;

import java.util.List;

public class MedicalRecordsServiceImpl implements MedicalRecordsService {

    @Override
    public List<MedicalRecordDTO> getAllMedicalRecords() {
        return List.of();
    }

    @Override
    public MedicalRecordDTO getMedicalRecordById(Long id) {
        return null;
    }

    @Override
    public List<MedicalRecordDTO> getMedicalRecordsByPatientId(Long id) {
        return List.of();
    }

    @Override
    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        return null;
    }

    @Override
    public void deleteMedicalRecord(Long id) {

    }
}
