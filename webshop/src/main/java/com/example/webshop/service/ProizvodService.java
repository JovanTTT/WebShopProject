package com.example.webshop.service;

import com.example.webshop.DTO.KategorijaDTO;
import com.example.webshop.DTO.ProizvodDTO;
import com.example.webshop.model.Kategorija;
import com.example.webshop.model.Proizvod;
import com.example.webshop.repository.ProizvodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProizvodService {

    @Autowired
    private ProizvodRepository proizvodRepository;


    public ProizvodDTO findOne(Long id){

        Optional<Proizvod> foundProizvod = proizvodRepository.findById(id);

        if (foundProizvod.isPresent()) {

            Proizvod proizvod=foundProizvod.get();
            ProizvodDTO proizvodDTO=new ProizvodDTO();
            proizvodDTO.setNaziv(proizvod.getNaziv());
            proizvodDTO.setCena(proizvod.getCena());
            proizvodDTO.setTipProdaje(proizvod.getTipProdaje());
            proizvodDTO.setSlikaProizvoda(proizvod.getSlikaProizvoda());
            proizvodDTO.setOpis(proizvod.getOpis());
            proizvodDTO.setId(proizvod.getId());
            Set<Kategorija> kategorije=proizvod.getKategorija();
            Set<KategorijaDTO> kategorijeDTO=new HashSet<>();
            for(Kategorija kategorija: kategorije){
                KategorijaDTO kategorijaDTO= new KategorijaDTO();
                kategorijaDTO.setNazivKategorije(kategorija.getNazivKategorije());
                kategorijaDTO.setId(kategorija.getId());
                kategorijeDTO.add(kategorijaDTO);
            }

            proizvodDTO.setKategorije(kategorijeDTO);
            return proizvodDTO;
        }
        return null;
    }

}
