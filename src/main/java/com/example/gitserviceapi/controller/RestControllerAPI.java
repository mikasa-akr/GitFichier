package com.example.gitserviceapi.controller;

import com.example.gitserviceapi.entity.Fichier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/fichier")
public class RestControllerAPI {
    private static final String FILE_PATH = "C:\\Users\\User\\Desktop\\data.txt";

    @GetMapping
    public List<Fichier> listRepositories(){
        // Read data from the file and return a list of Fichier objects
        List<Fichier> fichiers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Fichier fichier = new Fichier();
                fichier.setId(Integer.valueOf(data[0]));
                fichier.setLogin(String.valueOf(1));
                fichier.setFull_name(data[2]);
                fichier.setDescription(data[3]);
                fichier.setLanguage(data[4]);
                fichier.setAvatar_url(data[5]);
                fichier.setHtml_url(data[6]);
                fichiers.add(fichier);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fichiers;
    }
    @PostMapping
    public void addRepositories(@RequestBody Fichier fichier){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String line = fichier.getId() + "," + fichier.getLogin() + "," +fichier.getFull_name() + "," + fichier.getDescription() + "," +fichier.getLanguage() + "," + fichier.getAvatar_url() + "," +fichier.getHtml_url() + "," + System.lineSeparator();
            writer.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
