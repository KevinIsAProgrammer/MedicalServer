package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class PatientController {
    IPatientService service;

    @Autowired
    PatientController(@Qualifier("databasePatientService") IPatientService s) {
        this.service = s;
    }

    @PostMapping("/create")
    UUID addPatient(@RequestBody Patient p) {
        System.out.println(p); return service.create(p);
    }

    @GetMapping("/read")
    Patient getPatientByID(@RequestParam UUID id) {
        return service.read(id);
    }

    @GetMapping("/readAll")
    List<Patient> getAllPatients() {
        return service.readAll();
    }

    @GetMapping("/find")
    List<Patient> findPatientByFirstName(@RequestParam String firstName) {
        return service.find(firstName);
    }
}
