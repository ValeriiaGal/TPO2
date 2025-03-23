package com.example.tpo2;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class EntryRepository {
    private final List<Entry> entries = new ArrayList<>();

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public List<Entry> getAllEntries() {
        return entries;
    }

    public Entry getRandomEntry() {
        if (entries.isEmpty()) return null;
        Random random = new Random();
        return entries.get(random.nextInt(entries.size()));
    }
}
