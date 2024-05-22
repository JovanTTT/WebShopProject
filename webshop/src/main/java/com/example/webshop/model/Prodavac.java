package com.example.webshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Prodavac extends Korisnik{

    @OneToMany(mappedBy = "prodavac", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Proizvod> proizvodiNaProdaju = new HashSet<>();

    @Column
    private Double prosecnaOcena;

    @ElementCollection
    private Map<String, Integer> ocene = new HashMap<>(); // Kupac ID, Ocena

    @ElementCollection
    private Map<String, String> komentari = new HashMap<>(); // Kupac ID, Komentar

}
