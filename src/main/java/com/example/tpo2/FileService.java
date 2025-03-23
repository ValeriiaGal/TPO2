package com.example.tpo2;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class FileService {
    @Value("${pl.edu.pja.tpo02.filename}")
    private String filename;

    private final EntryRepository repository;

    public FileService(EntryRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void loadEntries() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    repository.addEntry(new Entry(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveEntryToFile(Entry entry) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, true), StandardCharsets.UTF_8))) {
            writer.write(entry.getPolish() + "," + entry.getEnglish() + "," + entry.getGerman());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving entry to file: " + e.getMessage());
        }
    }
}
