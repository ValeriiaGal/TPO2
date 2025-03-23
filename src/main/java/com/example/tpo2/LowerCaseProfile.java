package com.example.tpo2;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Profile("lowercase")
public class LowerCaseProfile implements DisplayProfile {
    @Override
    public void display(List<Entry> entries) {
        entries.forEach(e ->
                System.out.println(e.getPolish().toLowerCase() + " - "
                        + e.getEnglish().toLowerCase() + " - "
                        + e.getGerman().toLowerCase()));
    }
}

