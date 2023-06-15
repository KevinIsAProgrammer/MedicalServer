package org.example;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDatabasePatientService extends TestPatientService {
    @Autowired
    DatabasePatientService service;

    public IPatientService getService() { return service;}
}
