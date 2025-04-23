package edu.unimag.consultoriomedico.service.impl;

import edu.unimag.consultoriomedico.dto.DoctorDTO;
import edu.unimag.consultoriomedico.service.DoctorService;

import java.util.List;

public class DoctorServiceImpl implements DoctorService {

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
        return null;
    }

    @Override
    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDto) {
        return null;
    }

    @Override
    public void deleteDoctor(Long id) {

    }
}
