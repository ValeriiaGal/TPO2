package com.example.tpo2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.Scanner;

@Service
public class FlashcardsController implements EnvironmentAware {
    private final EntryRepository repository;
    private final DisplayProfile displayProfile;
    private final FileService fileService;
    private Environment environment;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    public FlashcardsController(EntryRepository repository, DisplayProfile displayProfile, FileService fileService) {
        this.repository = repository;
        this.displayProfile = displayProfile;
        this.fileService = fileService;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        while (true) {
            System.out.println("1. Add new word");
            System.out.println("2. Show all words");
            System.out.println("3. Take a test");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Polish translation: ");
                    String polish = scanner.nextLine();
                    System.out.print("Enter English translation: ");
                    String english = scanner.nextLine();
                    System.out.print("Enter German translation: ");
                    String german = scanner.nextLine();

                    Entry newEntry = new Entry(polish, english, german);
                    repository.addEntry(newEntry);

                    fileService.saveEntryToFile(newEntry);

                    System.out.println("Word added successfully!");
                    break;
                case 2:
                    displayProfile.display(repository.getAllEntries());
                    break;
                case 3:
                    Entry entry = repository.getRandomEntry();
                    if (entry != null) {
                        int languageToTranslate = random.nextInt(3); // 0 = Polish, 1 = English, 2 = German

                        boolean isUppercase = "uppercase".equalsIgnoreCase(activeProfile);

                        switch (languageToTranslate) {
                            case 0: // Polish -> English and German
                                String polishWord = isUppercase ? entry.getPolish().toUpperCase() : entry.getPolish().toLowerCase();
                                System.out.println("Translate " + polishWord + " to English: ");
                                String enAnswer = scanner.nextLine();
                                String germanWord = isUppercase ? entry.getPolish().toUpperCase() : entry.getGerman().toLowerCase();
                                System.out.println("Translate " + polishWord + " to German: ");
                                String deAnswer = scanner.nextLine();

                                if (entry.getEnglish().equalsIgnoreCase(enAnswer) && entry.getGerman().equalsIgnoreCase(deAnswer)) {
                                    System.out.println("Correct!");
                                } else {
                                    System.out.println("Incorrect. Correct answers: English - " + entry.getEnglish() + ", German - " + entry.getGerman());
                                }
                                break;

                            case 1: // English -> Polish and German
                                String englishWord = isUppercase ? entry.getEnglish().toUpperCase() : entry.getEnglish().toLowerCase();
                                System.out.println("Translate " + englishWord + " to Polish: ");
                                String plAnswer1 = scanner.nextLine();
                                String germanWord1 = isUppercase ? entry.getGerman().toUpperCase() : entry.getGerman().toLowerCase();
                                System.out.println("Translate " + englishWord + " to German: ");
                                String deAnswer1 = scanner.nextLine();

                                if (entry.getPolish().equalsIgnoreCase(plAnswer1) && entry.getGerman().equalsIgnoreCase(deAnswer1)) {
                                    System.out.println("Correct!");
                                } else {
                                    System.out.println("Incorrect. Correct answers: Polish - " + entry.getPolish() + ", German - " + entry.getGerman());
                                }
                                break;

                            case 2: // German -> Polish and English
                                String germanWord2 = isUppercase ? entry.getGerman().toUpperCase() : entry.getGerman().toLowerCase();
                                System.out.println("Translate " + germanWord2 + " to Polish: ");
                                String plAnswer2 = scanner.nextLine();
                                String englishWord2 = isUppercase ? entry.getEnglish().toUpperCase() : entry.getEnglish().toLowerCase();
                                System.out.println("Translate " + germanWord2 + " to English: ");
                                String enAnswer2 = scanner.nextLine();

                                if (entry.getPolish().equalsIgnoreCase(plAnswer2) && entry.getEnglish().equalsIgnoreCase(enAnswer2)) {
                                    System.out.println("Correct!");
                                } else {
                                    System.out.println("Incorrect. Correct answers: Polish - " + entry.getPolish() + ", English - " + entry.getEnglish());
                                }
                                break;
                        }
                    } else {
                        System.out.println("No words in dictionary.");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
