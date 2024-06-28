package com.example.webshop.service;

import com.example.webshop.model.Kategorija;
import com.example.webshop.repository.KategorijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KategorijaService {

    @Autowired
    private KategorijaRepository kategorijaRepository;

    public boolean proveriPostojanjeKategorije(String nazivKategorije) {

        Kategorija kategorija = kategorijaRepository.findByNazivKategorije(nazivKategorije);
        return kategorija!=null;
    }

    public void dodajNovuKategoriju(String nazivKategorije) {

        Kategorija novaKategorija = new Kategorija();
        novaKategorija.setNazivKategorije(nazivKategorije);

        kategorijaRepository.save(novaKategorija);
    }
}
