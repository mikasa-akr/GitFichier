package com.example.gitserviceapi.entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fichier {
    private String login;
    private String avatar_url;
    private Integer id;
    private String full_name;
    private String description;
    private String html_url;
    private String language;
}
