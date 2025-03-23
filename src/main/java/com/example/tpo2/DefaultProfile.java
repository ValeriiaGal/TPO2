package com.example.tpo2;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Profile("default")
public class DefaultProfile implements DisplayProfile {
    @Override
    public void display(List<Entry> entries) {
        entries.forEach(e ->
                System.out.println(e.getPolish() + " - " + e.getEnglish() + " - " + e.getGerman()));
    }
}
