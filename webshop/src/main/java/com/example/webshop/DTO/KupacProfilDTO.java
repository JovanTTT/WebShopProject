package com.example.webshop.DTO;


import com.example.webshop.model.Recenzija;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KupacProfilDTO {

    private Long id;

    private String ime;

    private String prezime;

    private String korisnickoIme;

    private Date datumRodjenja;

    private String slika;

    private String opisKorisnika;

    private Double prosecnaOcena;

    private Set<ProizvodiNaProdajuDTO> kupljeniProizvodi = new HashSet<>();

    private Set<Recenzija> dobijeneRecenzije = new HashSet<>();

}
