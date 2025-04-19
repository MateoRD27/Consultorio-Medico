package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    Patient patient1, patient2;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();
        patient1 = patientRepository.save(Patient.builder()
                .fullName("Javier Florez")
                .identificationNumber(123456789L)
                .email("jeferson.florez@gmail.com")
                .phoneNumber("322-222-22")
                .build());

        patient2 = patientRepository.save(Patient.builder()
                .fullName("Ali Cajar")
                .identificationNumber(16789L)
                .email("ali.cajar@gmail.com")
                .phoneNumber("444-333")
                .build());
    }

    @Test
    void shouldSaveAndFindPatient() {
        Patient patientTest = patientRepository.save(Patient.builder()
                .fullName("Deal Ramos")
                .identificationNumber(1678955L)
                .email("deal.ramos@gmail.com")
                .phoneNumber("333-333")
                .build());

        Optional<Patient> result = patientRepository.findById(patientTest.getId());

        assertTrue(result.isPresent());
        assertEquals("Deal Ramos", result.get().getFullName());
    }

    @Test
    void shouldFindAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        assertEquals(2, patients.size());
    }


    @Test
    void findByEmail() {
        Optional<Patient> result = patientRepository.findByEmail("jeferson.florez@gmail.com");
        assertTrue(result.isPresent());
        assertEquals("jeferson.florez@gmail.com", result.get().getEmail());
    }

    @Test
    void shouldUpdatePatient() {
        patient1.setPhoneNumber("2222-22553");
        Patient updated = patientRepository.save(patient1);

        assertEquals("2222-22553", updated.getPhoneNumber());
    }

    @Test
    void shouldDeletePatient() {
        Long id = patient2.getId();
        patientRepository.deleteById(id);
        assertFalse(patientRepository.findById(id).isPresent());
    }

}