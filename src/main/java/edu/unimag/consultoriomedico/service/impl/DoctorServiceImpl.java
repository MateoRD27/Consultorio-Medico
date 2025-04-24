package edu.unimag.consultoriomedico.service.impl;

import edu.unimag.consultoriomedico.dto.DoctorDTO;
import edu.unimag.consultoriomedico.entity.Doctor;
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
        return List.of();
    }

    @Override
    public DoctorDTO getDoctorById(Long id) {
        return null;
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDto) {
        //verificar si el doctor ya existe
        if(doctorRepository.findByIdentificationNumber(doctorDto.getIdentificationNumber()).isPresent()) {
            throw new UserAlreadyExistsException("Doctor already exists with identification number: " + doctorDto.getIdentificationNumber());
        }
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    @Override
    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDto) {
        return null;
    }

    @Override
    public void deleteDoctor(Long id) {

    }
}
