package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.TimeZone;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TestPatient {
    private ObjectMapper objectMapper;
    @Before
    public void setUp() throws Exception{
        objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getDefault());
        objectMapper.registerModule(new JavaTimeModule());
    }

    @After
    public void tearDown() throws Exception{
        objectMapper = null;
    }


    @Test
    public void testSerializing() throws JsonProcessingException {
        Patient p = new Patient();
        p.firstName = "Kevin";
        p.lastName  = "Hartmann";
        p.identifier = UUID.randomUUID();
        p.dateOfBirth = LocalDate.of(1972, 7,14);

        String jsonString = objectMapper.writeValueAsString(p);
        System.out.println(jsonString);
        assertThat(jsonString, containsString("\"dateOfBirth\":\"1972-07-14\""));
        assertThat(jsonString, containsString("\"firstName\":\"Kevin\""));
        assertThat(jsonString, containsString("\"lastName\":\"Hartmann\""));
    }

    private LocalDate date(int year, int month, int day) {
       return LocalDate.of(year, month, day);
    }

    @Test
    public void testDeserializing() throws JsonProcessingException {
        String jsonString = "{\"" +
                "identifier\":\"c938506e-cf6d-4696-9bd2-44d3e609c667\"," +
                "\"firstName\":\"Kevin\"," +
                "\"lastName\":\"Hartmann\"," +
                "\"dateOfBirth\":\"1972-07-14\"" +
                "}";
        Patient patient = objectMapper.readValue(jsonString, Patient.class);

        System.out.println(patient.toString());

        assertThat(patient.firstName, is("Kevin"));
        assertThat(patient.lastName, is("Hartmann"));
        assertThat(patient.dateOfBirth, equalTo(date(1972, 7, 14)));
        assertThat(patient.identifier, equalTo(UUID.fromString("c938506e-cf6d-4696-9bd2-44d3e609c667")));
    }
}