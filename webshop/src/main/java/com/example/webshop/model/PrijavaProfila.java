package com.example.webshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PrijavaProfila implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String razlogPrijave;

    @Column(nullable = true)
    private String razlogOdbijanja;

    @Column(nullable = true)
    private String razlogPrihvatanja;

    @Column(nullable = false)
    private Date datumPodnosenjaPrijave;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status statusPrijave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Korisnik podnosiocPrijave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Korisnik prijavljeniKorisnik;

}
