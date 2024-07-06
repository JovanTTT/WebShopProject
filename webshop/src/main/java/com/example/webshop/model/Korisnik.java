package com.example.webshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Korisnik")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Korisnik implements Serializable {

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
    //@JsonIgnore
    private Uloga uloga;

    @Column
    private Boolean blokiran;

    @OneToMany(mappedBy = "korisnikKojiJeDaoRecenziju", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Recenzija> recenzije = new HashSet<>();

    @OneToMany(mappedBy = "korisnikKojiJeDobioRecenziju", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Recenzija> dobijeneRecenzije = new HashSet<>();

    @OneToMany(mappedBy = "podnosiocPrijave", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<PrijavaProfila> prijava = new HashSet<>();

    @OneToMany(mappedBy = "prijavljeniKorisnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<PrijavaProfila> prijavljen = new HashSet<>();


}
