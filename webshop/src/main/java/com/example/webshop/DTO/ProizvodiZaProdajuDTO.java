package com.example.webshop.DTO;

import com.example.webshop.model.Kategorija;
import com.example.webshop.model.TipProdaje;
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
public class ProizvodiZaProdajuDTO {
    private Long id;

    private String naziv;

    private String opis;

    private String slikaProizvoda;

    private Set<Kategorija> kategorije = new HashSet<>();

    private Double cena;

    private TipProdaje tipProdaje;
}
