package org.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.lang.String.join;
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Patient {
    @Id
    UUID identifier;
    String firstName;
    String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth;

    @Override
    public String toString() {
        return join(" ", List.of(str(identifier),str(firstName), str(lastName), str(dateOfBirth)));
    }
    private static String str(Object o) {
        if (o == null) {
            return "Unknown";
        }
        String s = o.toString();
        if ("".equals(s)) {
            return "N/A";
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Patient) && (this.toString().equals(o.toString()));
    }
}
