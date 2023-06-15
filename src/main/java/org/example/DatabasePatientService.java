package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DatabasePatientService implements IPatientService {
    private final PatientRepository repository;

    @Autowired
    DatabasePatientService(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Patient read(UUID id) {
        return repository.findById(id).
                orElseThrow(() ->new PatientNotFoundException("Can't find patient with identifier "+id));
    }

    @Override
    public List<Patient> readAll() {
        List<Patient> patients = new ArrayList<>();
        repository.findAll().forEach(patients::add);
        return patients;
    }

    @Override
    public UUID create(Patient p) {
        p.identifier = UUID.randomUUID();
        Patient p2 = repository.save(p);
        return p2.identifier;
    }

    @Override
    public List<Patient> find(String firstName) {
        return repository.findPatientsByFirstName(firstName);
    }
}
