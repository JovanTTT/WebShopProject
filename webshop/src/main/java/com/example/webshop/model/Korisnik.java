package com.example.webshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Korisnik")
@Entity
@Getter
@Setter
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ime;

    @Column
    private String prezime;

    @Column(nullable = false)
    private String korisnickoIme;

    @Column(nullable = false)
    //,unique = true
    //@Email(message = "Invalid mail")
    private String mejl;

    @Column
    // @Length(max=40, message = "Neispravan broj telefona!")
    private String telefon;

    @Column(nullable = false)
    //@Pattern(regexp = "^(?=.?[A-Z])(?=.?[a-z])(?=.?[0-9])(?=.?[#?!@$%^&*-]).{8,}$")
    private String lozinka;

    @Column
    private Date datumRodjenja;

    @Column
    private String slika;

    @Column
    private String opisKorisnika;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonIgnore
    private Uloga uloga;

    @Column
    private Boolean blokiran;

    

}
