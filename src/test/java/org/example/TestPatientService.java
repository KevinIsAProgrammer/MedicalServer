package org.example;


import static org.junit.Assert. *;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Calendar;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

abstract public class TestPatientService{
        abstract IPatientService getService();
        @Test
        public void testNullPatient() {
            IPatientService service = getService();
            Throwable caught = null;

            try {
                service.create(null);
            } catch (Throwable t) {
                caught = t;
            }
            assertNotNull("Null patient should throw an exception", caught);
        }

        @Test
        public void testRead() {
            IPatientService service = getService();
            Throwable caught = null;

            try {
                service.read(UUID.randomUUID());
            } catch (Throwable t) {
                caught = t;
            }
            assertNotNull("Read patient should throw an exception for empty database", caught);
            assertTrue("Exception has right class", caught instanceof PatientNotFoundException);
        }

        @Test
        public void testAddEmptyPatient() {
            // We want to be able to record the fact that we don't know the patient's name or date of
            // birth or both (a patient admitted to the ER may not be conscious to ask), and we may still want the
            // unique id for that patient to use to record other information about them.
            //
            Patient p = new Patient();
            IPatientService service = getService();
            UUID id = service.create(p);
            assertNotNull("Identifier is not null", id);
        }

    @Test
    public void testAddJustId() {
        Patient p = new Patient();
        UUID id = UUID.randomUUID();
        p.identifier = id;
        IPatientService service = getService();

        UUID result = service.create(p);
        assertNotEquals("New id should have been chosen", id, result);
    }

    @Test
    public void testAddMultiplePatients() {
       List<Patient> patients = Collections.nCopies(100, testPatient1());
       testPatientList(patients);
    }

    public void testPatientList(List<Patient> patients) {
        IPatientService service = getService();
        for(Patient p: patients) {
            testAddPatientInvariants(service, p);
        }
    }

    public void testAddPatientInvariants(IPatientService service, Patient newPatient) {
        List<Patient> oldPatients = service.readAll();
        int sizeBefore = oldPatients.size();

        UUID id = service.create(newPatient);
        assertNotNull("Id should not be null", id);
        newPatient.identifier = id;

        // reading back from the service should now return the new patient
        testRead(service, newPatient);

        // reading back from the service should still return all the old patients
        for(Patient old: oldPatients) {
            testRead(service, old);
        }

        List<Patient> newPatients = service.readAll();
        int sizeAfter = newPatients.size();
        assertEquals("Size of all patients should go up by 1", sizeBefore + 1, sizeAfter);

        assertTrue(newPatients.contains(newPatient));
        assertTrue(newPatients.containsAll(oldPatients));
    }

    public void testRead(IPatientService service, Patient p) {
        Patient p2 = service.read(p.identifier);
        assertEquals("Patient read failure", p, p2);
    }

    @Test
    public void testFind() {
        IPatientService service = getService();

        List<Patient> patients = service.find("Kevin");
        assertEquals("Ensure we don't find a missing patient", Collections.emptyList(), patients);
        UUID id = service.create(testPatient1());
        service.create(testPatient2());

        patients = service.find("Kevin");
        assertEquals("Ensure we get the right number of results", 1, patients.size());
        Patient found = patients.get(0);

        assertEquals("Ensure we found the right patient", id, found.identifier);
    }

    // test fixtures
    Patient testPatient1() {
        Patient p = new Patient();
        p.firstName = "Kevin";
        p.lastName = "Hartmann";
        p.dateOfBirth = LocalDate.of(1972, Calendar.JULY, 14);

        return p;
    }
    Patient testPatient2() {
        Patient p = new Patient();
        p.firstName = "Jen";
        p.lastName = "Plank";
        p.dateOfBirth = LocalDate.of(1969, Calendar.DECEMBER, 5);

        return p;
    }
}
