package org.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PatientRepository extends CrudRepository<Patient, UUID> {
    List<Patient> findPatientsByFirstName(String firstName);
}