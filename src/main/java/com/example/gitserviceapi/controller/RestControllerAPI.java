package com.example.gitserviceapi.controller;

import com.example.gitserviceapi.entity.Fichier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/fichier")
public class RestControllerAPI {
    private static final String FILE_PATH = "C:\\Users\\User\\Desktop\\data.txt";

    private static final Logger logger = LoggerFactory.getLogger(RestControllerAPI.class);

    @GetMapping
    public ResponseEntity<List<Fichier>> listRepositories() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            List<Fichier> fichiers = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                Fichier fichier = mapToEntity(line);
                fichiers.add(fichier);
            }
            return ResponseEntity.ok(fichiers);
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            logger.error("Error reading file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addRepository(@RequestBody Fichier fichier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String line = mapToString(fichier);
            writer.write(line);

            // Log the received data
            logger.info("Received repository data: " + line);

            return ResponseEntity.ok("Repository added successfully.");
        } catch (IOException e) {
            // Log the exception
            logger.error("Error adding repository", e);
            return ResponseEntity.status(500).body("Error adding repository: " + e.getMessage());
        }
    }



    private Fichier mapToEntity(String line) {
        String[] data = line.split(",");
        Fichier fichier = new Fichier();
        fichier.setId(data[0]);
        fichier.setFull_name(data[1]);
        fichier.setDescription(data[2]);
        fichier.setLanguage(data[3]);
        fichier.setAvatar_url(data[4]);
        fichier.setHtml_url(data[5]);
        return fichier;
    }

    private String mapToString(Fichier fichier) {
        return fichier.getId() + "," + fichier.getFull_name() + "," + fichier.getDescription() + "," +
                fichier.getLanguage() + "," + fichier.getAvatar_url() + "," + fichier.getHtml_url() + System.lineSeparator();
    }
    @DeleteMapping("/{Repoid}")
    public ResponseEntity<String> deleteRepository(@PathVariable String Repoid) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            Iterator<String> iterator = lines.iterator();

            System.out.println("Deleting repository with Repoid: " + Repoid);

            while (iterator.hasNext()) {
                String line = iterator.next();
                System.out.println("Current line: " + line);
                if (line.startsWith(Repoid + ",")) {
                    iterator.remove();
                    System.out.println("Line removed.");
                    break;
                }
            }

            System.out.println("Updated lines: " + lines);

            Files.write(Paths.get(FILE_PATH), lines);

            return ResponseEntity.ok("Repository deleted successfully.");
        } catch (IOException e) {
            logger.error("Error deleting repository", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting repository: " + e.getMessage());
        }
    }


}