package com.example.webshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Kupac extends Korisnik{

    @Column
    private Double prosecnaOcena;



    @ElementCollection
    private Map<String, Integer> ocene = new HashMap<>(); // Kupac ID, Ocena

    @ElementCollection
    private Map<String, String> komentari = new HashMap<>(); // Kupac ID, Komentar


}
