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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Proizvod implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String naziv;

    @Column
    private String opis;

    @Column
    private String slikaProizvoda;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "proizvod_kategorija",
            joinColumns = {
                    @JoinColumn(name = "proizvod_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "kategorija_id", referencedColumnName = "id")
            }
    )
    //  @JsonIgnore
    private Set<Kategorija> kategorija = new HashSet<>();
/*
   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Kategorija  kategorija;*/

    //private ArrayList<Kategorija> kategorija = new ArrayList<>();
    @Column
    private Double cena;

    @Enumerated(EnumType.STRING)
    @Column
    private TipProdaje tipProdaje;

    @Column
    private Date datumObjavljivanja;

    @Column
    private Boolean recenzijaKupac;

    @Column
    private Boolean recenzijaProdavac;

    @Column
    private Boolean prodat;

    @OneToMany(mappedBy = "proizvod", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Ponuda> ponude =new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Prodavac prodavac;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Kupac kupac;
}
