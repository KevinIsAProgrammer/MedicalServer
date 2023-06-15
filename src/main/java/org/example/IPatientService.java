package org.example;

import java.util.List;
import java.util.UUID;

public interface IPatientService {
    Patient read(UUID id);
    List<Patient> readAll();
    UUID create(Patient p);
    List<Patient> find(String firstName);
}
