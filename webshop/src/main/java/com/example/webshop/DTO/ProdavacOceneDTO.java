package com.example.webshop.DTO;

import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProdavacOceneDTO {

    private String ime;

    private String prezime;

    private String korisnickoIme;

    private String slika;

    private Double prosecnaOcena;

    @ElementCollection
    private Map<String, Integer> ocene = new HashMap<>(); // Kupac ID, Ocena

    @ElementCollection
    private Map<String, String> komentari = new HashMap<>(); // Kupac ID, Komentar

}
