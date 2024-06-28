package com.example.webshop.service;

import com.example.webshop.DTO.KategorijaDTO;
import com.example.webshop.model.Kategorija;
import com.example.webshop.repository.KategorijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<KategorijaDTO> findAll() {

        List<Kategorija>kategorije= kategorijaRepository.findAll();
        List<KategorijaDTO>kategorijeDTO=new ArrayList<>();
        List<Kategorija> slanje=new ArrayList<>();

        for(Kategorija k:kategorije){
            KategorijaDTO kategorijaDTO=new KategorijaDTO();
            kategorijaDTO.setNazivKategorije(k.getNazivKategorije());
            // kategorijaDTO.setId(k.getId());
            kategorijeDTO.add(kategorijaDTO);
        }

        return kategorijeDTO;
    }
}
