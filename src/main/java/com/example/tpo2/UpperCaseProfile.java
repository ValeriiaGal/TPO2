package com.example.tpo2;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Profile("uppercase")
public class UpperCaseProfile implements DisplayProfile {
    @Override
    public void display(List<Entry> entries) {
        entries.forEach(e ->
                System.out.println(e.getPolish().toUpperCase() + " - "
                        + e.getEnglish().toUpperCase() + " - "
                        + e.getGerman().toUpperCase()));
    }
}
