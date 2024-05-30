package com.example.webshop.DTO;

import com.example.webshop.model.Recenzija;
import com.example.webshop.model.Uloga;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdavacProfilDTO {

    private String ime;

    private String prezime;

    private String korisnickoIme;

    private String telefon;

    private String slika;

    private String opisKorisnika;

    private Uloga uloga;

    private Boolean blokiran;

    private Set<ProizvodiNaProdajuDTO> proizvodiNaProdaju = new HashSet<>();

    private Set<Recenzija> dobijeneRecenzije = new HashSet<>();

    private Double prosecnaOcena;

}
