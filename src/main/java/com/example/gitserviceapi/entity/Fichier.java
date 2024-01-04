package com.example.gitserviceapi.entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fichier {
    private String id;
    private String full_name;
    private String description;
    private String language;
    private String avatar_url;
    private String html_url;
}
