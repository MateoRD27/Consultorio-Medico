package edu.unimag.consultoriomedico.service.impl;

import edu.unimag.consultoriomedico.dto.MedicalRecordDTO;
import edu.unimag.consultoriomedico.entity.Appointment;
import edu.unimag.consultoriomedico.entity.MedicalRecord;
import edu.unimag.consultoriomedico.entity.Patient;
import edu.unimag.consultoriomedico.enums.Status;
import edu.unimag.consultoriomedico.exception.AppointmentNotCompletedException;
import edu.unimag.consultoriomedico.exception.ResourceNotFoundException;
import edu.unimag.consultoriomedico.mapper.MedicalRecordMapper;
import edu.unimag.consultoriomedico.repository.AppointmentRepository;
import edu.unimag.consultoriomedico.repository.MedicalRecordRepository;
import edu.unimag.consultoriomedico.repository.PatientRepository;
import edu.unimag.consultoriomedico.service.MedicalRecordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordsServiceImpl implements MedicalRecordsService {
    private  final MedicalRecordRepository medicalRecordRepository;
    private  final MedicalRecordMapper medicalRecordMapper;
    private  final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    @Override
    public List<MedicalRecordDTO> getAllMedicalRecords() {
        System.out.println("GET /api/records endpoint llamado!");
        List<MedicalRecordDTO> listMedicalRecords= medicalRecordRepository.findAll().stream().map(medicalRecordMapper::toDTO).toList();
        if(listMedicalRecords.isEmpty()){
            throw new ResourceNotFoundException("Medical Records not found ");
        }
        System.out.println("Devolviendo " + listMedicalRecords.size() + " registros");
        return listMedicalRecords;
    }

    @Override
    public MedicalRecordDTO getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id)
                .map(medicalRecordMapper::toDTO)
                .orElseThrow(()->new ResourceNotFoundException("Medical Records not found"));
    }

    @Override
    public List<MedicalRecordDTO> getMedicalRecordsByPatientId(Long id) {
        List<MedicalRecordDTO> listMedicalRecordsPatient = medicalRecordRepository.findByPatientId(id).stream()
                .map(medicalRecordMapper::toDTO).toList();

        if(listMedicalRecordsPatient.isEmpty()){
            throw new ResourceNotFoundException("Medical Records not found ");
        }
        return listMedicalRecordsPatient;
    }

    @Override
    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        Appointment appointment = appointmentRepository.findById(medicalRecordDTO.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + medicalRecordDTO.getAppointmentId()));

        if (appointment.getStatus() != Status.COMPLETED) {
            throw new AppointmentNotCompletedException("Cannot create medical record: appointment with ID " + medicalRecordDTO.getAppointmentId() + " has not been completed.");
        }


        Patient patient = patientRepository.findById(medicalRecordDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + medicalRecordDTO.getPatientId()));

        // Mapear DTO a entidad
        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(medicalRecordDTO);
        medicalRecord.setAppointment(appointment);
        medicalRecord.setPatient(patient); // Asignar paciente

        // Guardar historial médico y devolver como DTO
        return medicalRecordMapper.toDTO(medicalRecordRepository.save(medicalRecord));
    }

    @Override
    public void deleteMedicalRecord(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medical Record not found with ID: " + id);
        }
        medicalRecordRepository.deleteById(id);
    }
}
