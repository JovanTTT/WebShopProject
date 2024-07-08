package com.example.webshop.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecenzijaPrikaz3DTO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private KupacPrikazRecenzijeDTO recenzijuPodneo;

    private ProdavacPrikazRecenzijeDTO recenzijuPrimio;

    private String komentar;

    private Date datumPodnosenjaRecenzije;

    private Double ocena;

}
