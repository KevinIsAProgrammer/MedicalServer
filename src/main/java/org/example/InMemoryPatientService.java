package org.example;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InMemoryPatientService implements IPatientService{
    Map<UUID, Patient> map;

    InMemoryPatientService() {
        map=new HashMap<>();
    }

    public Patient read(UUID id) {
        if (map.containsKey(id)) {
            return map.get(id);
        }
        throw new PatientNotFoundException("Can't find a patient with identifier " + id);
    }

    public List<Patient> readAll() {
        return List.copyOf(map.values());
    }

    public UUID create(Patient p) {
        UUID id = UUID.randomUUID();
        p.identifier = id;
        map.put(id, p);
        return id;
    }

    public List<Patient> find(String firstName) {
        return map.values().stream().
                filter(e -> e.firstName.equals(firstName)).
                collect(Collectors.toList());
    }
}
