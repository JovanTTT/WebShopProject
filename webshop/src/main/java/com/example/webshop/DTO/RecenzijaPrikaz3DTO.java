package com.example.webshop.DTO;

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

    private KupacPrikazRecenzijeDTO recenzijuPodneo;

    private ProdavacPrikazRecenzijeDTO recenzijuPrimio;

    private String komentar;

    private Date datumPodnosenjaRecenzije;

    private Double ocena;

}
