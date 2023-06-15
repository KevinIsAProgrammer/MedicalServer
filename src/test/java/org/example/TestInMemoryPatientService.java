package org.example;

public class TestInMemoryPatientService extends TestPatientService {
    public IPatientService getService() {
       return new InMemoryPatientService();
    }
}
