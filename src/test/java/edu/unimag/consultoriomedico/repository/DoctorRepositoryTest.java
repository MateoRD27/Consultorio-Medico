package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DoctorRepositoryTest {
    @Autowired
    private DoctorRepository doctorRepository;

    Doctor doctor1, doctor2;

    @BeforeEach
    void setUp() {
        doctorRepository.deleteAll();
        doctor1 =doctorRepository.save(Doctor.builder()
                .fullName("Dr. Florez")
                .identificationNumber(123456789L)
                .specialty("Medicina General")
                .email("doctor.florez@gmail.com")
                .availableFrom(LocalTime.of(8, 0))
                .availableTo(LocalTime.of(17, 0))
                .build());

        doctor2 =doctorRepository.save(Doctor.builder()
                .fullName("Dr. Cantillo")
                .identificationNumber(12345L)
                .specialty("Cirujano")
                .email("doctor.cantillo@gmail.com")
                .availableFrom(LocalTime.of(8, 0))
                .availableTo(LocalTime.of(17, 0))
                .build());
    }

    @Test
    void shouldSaveAndFindDoctor() {
        Doctor doctorTest = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Ramos")
                .identificationNumber(21222L)
                .specialty("Pediatra")
                .email("doctor.ramos@gmail.com")
                .availableFrom(LocalTime.of(8, 0))
                .availableTo(LocalTime.of(17, 0))
                .build());

        Optional<Doctor> result = doctorRepository.findById(doctorTest.getId());

        assertTrue(result.isPresent());
        assertEquals("Dr. Ramos", result.get().getFullName());
    }

    @Test
    void shouldFindAllDoctors() {
        List<Doctor> rooms = doctorRepository.findAll();
        //dos doctores son cargados antes
        assertEquals(2, rooms.size());
    }

    @Test
    void shouldFindBySpecialty(){
        List<Doctor> result = doctorRepository.findBySpecialty("Medicina General");
        assertEquals(1, result.size());
    }

    @Test
    void shouldUpdateDoctor() {
        doctor1.setAvailableFrom(LocalTime.of(5, 0));
        Doctor updated = doctorRepository.save(doctor1);

        assertEquals(LocalTime.of(5, 0), updated.getAvailableFrom());
    }

    @Test
    void shouldDeleteDoctor() {
        Long id = doctor2.getId();
        doctorRepository.deleteById(id);
        assertFalse(doctorRepository.findById(id).isPresent());
    }

    @Test
    void findByAvailableFromLessThanEqualAndAvailableToGreaterThanEqual() {
        List<Doctor> result = doctorRepository.findByAvailableFromLessThanEqualAndAvailableToGreaterThanEqual(LocalTime.of(9, 0), LocalTime.of(16, 0));
        assertEquals(2, result.size());
    }

    @Test
    void findByIdentificationNumberAndAvailableFromLessThanEqualAndAvailableToGreaterThanEqual() {
        Optional<Doctor> result = doctorRepository.findByIdentificationNumberAndAvailableFromLessThanEqualAndAvailableToGreaterThanEqual(123456789L, LocalTime.of(9, 0), LocalTime.of(16, 0));
        assertTrue(result.isPresent());
        assertEquals("Dr. Florez", result.get().getFullName());
    }

    @Test
    void findByIdentificationNumber() {
        Optional<Doctor> result = doctorRepository.findByIdentificationNumber(123456789L);
        assertTrue(result.isPresent());
        assertEquals("Dr. Florez", result.get().getFullName());
    }
}