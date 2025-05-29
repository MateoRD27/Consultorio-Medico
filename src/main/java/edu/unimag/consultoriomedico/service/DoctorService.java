package edu.unimag.consultoriomedico.service;

import edu.unimag.consultoriomedico.dto.DoctorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService {
    //obtener todos los doctores
    List<DoctorDTO> getAllDoctors();
    //obtener el doctor por su id
    DoctorDTO getDoctorById(Long id);

    //Obtener doctores por especialidad
    List<DoctorDTO> getDoctorsBySpecialty(String specialty);

    //crear un doctor
    DoctorDTO createDoctor(DoctorDTO doctorDto);
    //actualizar doctor
    DoctorDTO updateDoctor(Long id, DoctorDTO doctorDto);
    //eliminar doctor
    void deleteDoctor(Long id);




}
