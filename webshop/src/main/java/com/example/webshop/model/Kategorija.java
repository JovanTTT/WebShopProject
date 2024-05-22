package com.example.webshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Kategorija implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nazivKategorije;

    @ManyToMany(mappedBy = "kategorija", fetch = FetchType.LAZY)
    // @JsonIgnore
    private Set<Proizvod> proizvodi = new HashSet<>();

}
