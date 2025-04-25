package edu.unimag.consultoriomedico.service.impl;

import edu.unimag.consultoriomedico.dto.DoctorDTO;
import edu.unimag.consultoriomedico.entity.Doctor;
import edu.unimag.consultoriomedico.exception.ResourceNotFoundException;
import edu.unimag.consultoriomedico.exception.UserAlreadyExistsException;
import edu.unimag.consultoriomedico.mapper.DoctorMapper;
import edu.unimag.consultoriomedico.repository.DoctorRepository;
import edu.unimag.consultoriomedico.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private  final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;


    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDTO)
                .toList();
    }

    @Override
    public DoctorDTO getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(doctorMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
    }

    @Override
    public List<DoctorDTO> getDoctorsBySpecialty(String specialty) {
        List<DoctorDTO> listDoctor = doctorRepository.findBySpecialty(specialty).stream()
                .map(doctorMapper::toDTO)
                .toList();
        if(listDoctor.isEmpty()) {
            throw new ResourceNotFoundException("Doctors not found with special type: " + specialty);
        }
        return listDoctor;
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDto) {
        //verificar si el doctor ya existe
        if(doctorRepository.findByIdentificationNumber(doctorDto.getIdentificationNumber()).isPresent()) {
            throw new UserAlreadyExistsException("Doctor already exists with identification number: " + doctorDto.getIdentificationNumber());
        }
        // Mapear DTO a entidad
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        // Guardar doctor y como dto
        return doctorMapper.toDTO(doctorRepository.save(doctor));
    }

    @Override
    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDto) {
        Doctor existing = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
        existing.setFullName(doctorDto.getFullName());
        existing.setIdentificationNumber(doctorDto.getIdentificationNumber());
        existing.setSpecialty(doctorDto.getSpecialty());
        existing.setEmail(doctorDto.getEmail());
        existing.setAvailableFrom(doctorDto.getAvailableFrom());
        existing.setAvailableTo(doctorDto.getAvailableTo());

        return doctorMapper.toDTO(doctorRepository.save(existing));

    }

    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with ID: " + id);
        }
        doctorRepository.deleteById(id);
    }


}
