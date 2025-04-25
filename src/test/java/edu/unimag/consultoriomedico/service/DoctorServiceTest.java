package edu.unimag.consultoriomedico.service;

import edu.unimag.consultoriomedico.dto.DoctorDTO;
import edu.unimag.consultoriomedico.entity.Doctor;
import edu.unimag.consultoriomedico.exception.ResourceNotFoundException;
import edu.unimag.consultoriomedico.mapper.DoctorMapper;
import edu.unimag.consultoriomedico.repository.DoctorRepository;
import edu.unimag.consultoriomedico.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {
    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorMapper doctorMapper;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    DoctorDTO doctorDTO;
    Doctor doctor;


    @BeforeEach
    void setUp() {
        doctorRepository.deleteAll();
        doctorDTO = DoctorDTO.builder()
                .identificationNumber(123L)
                .fullName("Dr. Mateo")
                .specialty("Cardiology")
                .email("dr.new@example.com")
                .availableFrom(LocalTime.of(8, 0))
                .availableTo(LocalTime.of(16, 0))
                .build();

        doctor = Doctor.builder()
                .identificationNumber(123L)
                .fullName("Dr. Mateo")
                .specialty("Cardiology")
                .email("dr.new@example.com")
                .availableFrom(LocalTime.of(8, 0))
                .availableTo(LocalTime.of(16, 0))
                .build();
    }

    // Verifica que se obtenga correctamente la lista de doctores
    @Test
    void shouldGetAllDoctors() {
        when(doctorRepository.findAll()).thenReturn(List.of(doctor));
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);

        List<DoctorDTO> result = doctorService.getAllDoctors();

        assertEquals(1, result.size());
        assertEquals("Dr. Mateo", result.getFirst().getFullName());
    }

    // Verifica que se obtenga correctamente un doctor por su ID
    @Test
    void shouldGetDoctorById() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);

        DoctorDTO result = doctorService.getDoctorById(1L);

        assertEquals("Dr. Mateo", result.getFullName());
    }

    // Verifica que se obtengan los doctores por especialidad
    @Test
    void shouldGetDoctorsBySpecialty() {
        String specialty = "Cardiology";

        when(doctorRepository.findBySpecialty(specialty)).thenReturn(List.of(doctor));
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);

        List<DoctorDTO> result = doctorService.getDoctorsBySpecialty(specialty);

        assertEquals(1, result.size());
        assertEquals(specialty, result.getFirst().getSpecialty());
    }

    // Verifica que se cree un doctor correctamente
    @Test
    void shouldCreateDoctor() {
        when(doctorMapper.toEntity(doctorDTO)).thenReturn(doctor);
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);

        DoctorDTO result = doctorService.createDoctor(doctorDTO);

        assertEquals("Dr. Mateo", result.getFullName());
        verify(doctorRepository).save(doctor);
    }

    // Verifica que se actualice un doctor correctamente
    @Test
    void shouldUpdateDoctor() {
        Doctor existing = Doctor.builder().id(1L)
                .identificationNumber(12L)
                .fullName("Dr. Florez")
                .specialty("Cardiology")
                .email("dr.flow@example.com")
                .availableFrom(LocalTime.of(8, 0))
                .availableTo(LocalTime.of(16, 0))
                .build();
        DoctorDTO updatedDTO = DoctorDTO.builder()
                .identificationNumber(12L)
                .fullName("Dr. New")
                .specialty("General")
                .email("dr.flow@example.com")
                .availableFrom(LocalTime.of(8, 0))
                .availableTo(LocalTime.of(16, 0))
                .build();
        Doctor updated = Doctor.builder().id(1L)
                .identificationNumber(12L)
                .fullName("Dr. New")
                .specialty("General")
                .email("dr.flow@example.com")
                .availableFrom(LocalTime.of(8, 0))
                .availableTo(LocalTime.of(16, 0))
                .build();

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(doctorRepository.save(existing)).thenReturn(updated);
        when(doctorMapper.toDTO(updated)).thenReturn(updatedDTO);

        DoctorDTO result = doctorService.updateDoctor(1L, updatedDTO);

        assertEquals("Dr. New", result.getFullName());
    }

    // Verifica que se elimine un doctor correctamente
    @Test
    void shouldDeleteDoctor() {
        when(doctorRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> doctorService.deleteDoctor(1L));
        verify(doctorRepository).deleteById(1L);
    }

    //  verifica que si un doctor no se encuentra, se lance la excepción correspondiente
    @Test
    void shouldThrowIfDoctorNotFoundForGet() {
        when(doctorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> doctorService.getDoctorById(999L));
    }

    // verifica que si no se encuentra el doctor, se lance la excepción al intentar actualizarlo
    @Test
    void shouldThrowIfDoctorNotFoundForUpdate() {
        when(doctorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> doctorService.updateDoctor(999L, doctorDTO));
    }

    // Asegura que si el doctor no existe en la base de datos, no se podrá eliminar
    @Test
    void shouldThrowIfDoctorNotFoundForDelete() {
        when(doctorRepository.existsById(100L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> doctorService.deleteDoctor(100L));
    }


}