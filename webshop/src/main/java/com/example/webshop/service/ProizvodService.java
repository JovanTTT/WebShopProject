package com.example.webshop.service;

import com.example.webshop.DTO.*;
import com.example.webshop.error.PasswordMismatchException;
import com.example.webshop.error.ProductNotFoundException;
import com.example.webshop.model.Kategorija;
import com.example.webshop.model.Prodavac;
import com.example.webshop.model.Proizvod;
import com.example.webshop.model.TipProdaje;
import com.example.webshop.repository.ProizvodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<SviProizvodiDTO> findAllProducts() {

        List<Proizvod> proizvodi = proizvodRepository.findAll();
        List<SviProizvodiDTO> proizvodiDTO = new ArrayList<>();
        List<Proizvod> slanje = new ArrayList<>();

        for (Proizvod proizvod : proizvodi) {
            SviProizvodiDTO proizvodDTO = new SviProizvodiDTO();
            proizvodDTO.setId(proizvod.getId());
            proizvodDTO.setNaziv(proizvod.getNaziv());
            proizvodDTO.setOpis(proizvod.getOpis());
            proizvodDTO.setSlikaProizvoda(proizvod.getSlikaProizvoda());
            proizvodDTO.setCena(proizvod.getCena());
            proizvodDTO.setTipProdaje(proizvod.getTipProdaje());
            Set< Kategorija> kategorije=proizvod.getKategorija();
            Set<KategorijaDTO> kategorijeDTO=new HashSet<>();
            for(Kategorija kategorija: kategorije){
                KategorijaDTO kategorijaDTO= new KategorijaDTO();
                kategorijaDTO.setNazivKategorije(kategorija.getNazivKategorije());
                kategorijaDTO.setId(kategorija.getId());
                kategorijeDTO.add(kategorijaDTO);
            }

            proizvodDTO.setKategorije(kategorijeDTO);
            proizvodiDTO.add(proizvodDTO);
        }
        return proizvodiDTO;
    }

    public List<ProizvodDTO> findAll() {

        List<Proizvod> proizvodi = proizvodRepository.findAll();
        List<ProizvodDTO> proizvodiDTO = new ArrayList<>();
        List<Proizvod> slanje = new ArrayList<>();

        for (Proizvod proizvod : proizvodi) {
            ProizvodDTO proizvodDTO = new ProizvodDTO();
            proizvodDTO.setId(proizvod.getId());
            proizvodDTO.setNaziv(proizvod.getNaziv());
            proizvodDTO.setOpis(proizvod.getOpis());
            proizvodDTO.setSlikaProizvoda(proizvod.getSlikaProizvoda());
            proizvodiDTO.add(proizvodDTO);
        }
        return proizvodiDTO;
    }

    public List<ProizvodDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Proizvod> proizvodiPage = proizvodRepository.findAll(pageable);

        List<ProizvodDTO> proizvodiDTO = new ArrayList<>();
        for (Proizvod proizvod : proizvodiPage.getContent()) {
            ProizvodDTO proizvodDTO = new ProizvodDTO();
            proizvodDTO.setId(proizvod.getId());
            proizvodDTO.setNaziv(proizvod.getNaziv());
            proizvodDTO.setOpis(proizvod.getOpis());
            proizvodDTO.setSlikaProizvoda(proizvod.getSlikaProizvoda());
            proizvodDTO.setCena(proizvod.getCena());
            proizvodDTO.setTipProdaje(proizvod.getTipProdaje());
            Set< Kategorija> kategorije=proizvod.getKategorija();
            Set<KategorijaDTO> kategorijeDTO=new HashSet<>();
            for(Kategorija kategorija: kategorije){
                KategorijaDTO kategorijaDTO= new KategorijaDTO();
                kategorijaDTO.setNazivKategorije(kategorija.getNazivKategorije());
                kategorijaDTO.setId(kategorija.getId());
                kategorijeDTO.add(kategorijaDTO);
            }

            proizvodDTO.setKategorije(kategorijeDTO);
            // Postaviti ostale atribute po potrebi
            proizvodiDTO.add(proizvodDTO);
        }
        return proizvodiDTO;
    }

    public SviProizvodiDTO findProduct(Long id) {
        Optional<Proizvod> foundProizvod = proizvodRepository.findById(id);

        if (foundProizvod.isPresent()) {

            Proizvod proizvod=foundProizvod.get();
            SviProizvodiDTO proizvodDTO=new SviProizvodiDTO();
            proizvodDTO.setNaziv(proizvod.getNaziv());
            proizvodDTO.setCena(proizvod.getCena());
            proizvodDTO.setTipProdaje(proizvod.getTipProdaje());
            proizvodDTO.setSlikaProizvoda(proizvod.getSlikaProizvoda());
            proizvodDTO.setOpis(proizvod.getOpis());
            proizvodDTO.setId(proizvod.getId());
            Prodavac p=proizvod.getProdavac();
            ProdavacProfilDTO prodavacProfilDTO=new ProdavacProfilDTO();
            prodavacProfilDTO.setId(p.getId());
            prodavacProfilDTO.setIme(p.getIme());
            prodavacProfilDTO.setPrezime(p.getPrezime());
            prodavacProfilDTO.setBlokiran(p.getBlokiran());
            prodavacProfilDTO.setKorisnickoIme(p.getKorisnickoIme());
            prodavacProfilDTO.setSlika(p.getSlika());
            prodavacProfilDTO.setTelefon(p.getTelefon());
            prodavacProfilDTO.setDobijeneRecenzije(p.getDobijeneRecenzije());
            prodavacProfilDTO.setOpisKorisnika(p.getOpisKorisnika());
            prodavacProfilDTO.setProsecnaOcena(p.getProsecnaOcena());

            Set<ProizvodiNaProdajuDTO> proizvodiNaProdajuDTO = new HashSet<>();
            for (Proizvod prodNaProdaju : p.getProizvodiNaProdaju()) {
                ProizvodiNaProdajuDTO prodDTO = new ProizvodiNaProdajuDTO();
                prodDTO.setNaziv(prodNaProdaju.getNaziv());
                prodDTO.setCena(prodNaProdaju.getCena());
                // Dodajte ostala potrebna polja
                proizvodiNaProdajuDTO.add(prodDTO);
            }
            prodavacProfilDTO.setProizvodiNaProdaju(proizvodiNaProdajuDTO);

            proizvodDTO.setProdavac(prodavacProfilDTO);
            Set< Kategorija> kategorije=proizvod.getKategorija();
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

    public List<ProizvodDTO> findByNazivAndOpis(String name, String description) throws ProductNotFoundException {

        List<Proizvod> proizvodi = proizvodRepository.findByNazivAndOpis(name, description);
        List<ProizvodDTO> proizvodiDTO = new ArrayList<>();
        for(Proizvod proizvod: proizvodi){
            ProizvodDTO proizvodDTO = new ProizvodDTO();
            proizvodDTO.setId(proizvod.getId());
            proizvodDTO.setNaziv(proizvod.getNaziv());
            proizvodDTO.setOpis(proizvod.getOpis());
            proizvodDTO.setSlikaProizvoda(proizvod.getSlikaProizvoda());
            proizvodDTO.setCena(proizvod.getCena());
            proizvodDTO.setTipProdaje(proizvod.getTipProdaje());
            Set< Kategorija> kategorije=proizvod.getKategorija();
            Set<KategorijaDTO> kategorijeDTO=new HashSet<>();
            for(Kategorija kategorija: kategorije){
                KategorijaDTO kategorijaDTO= new KategorijaDTO();
                kategorijaDTO.setNazivKategorije(kategorija.getNazivKategorije());
                kategorijaDTO.setId(kategorija.getId());
                kategorijeDTO.add(kategorijaDTO);
            }

            proizvodDTO.setKategorije(kategorijeDTO);
            proizvodiDTO.add(proizvodDTO);
        }
        if (proizvodiDTO.isEmpty()) {

            throw new ProductNotFoundException("Trazeni proizvod ne postoji.");
        }
        return proizvodiDTO;
    }

    public List<ProizvodDTO> findByNaziv(String name) throws ProductNotFoundException, PasswordMismatchException {

        List<Proizvod> proizvodi = proizvodRepository.findByNaziv(name);
        List<ProizvodDTO> proizvodiDTO = new ArrayList<>();

        for(Proizvod proizvod: proizvodi){
            ProizvodDTO proizvodDTO = new ProizvodDTO();
            proizvodDTO.setId(proizvod.getId());
            proizvodDTO.setNaziv(proizvod.getNaziv());
            proizvodDTO.setOpis(proizvod.getOpis());
            proizvodDTO.setSlikaProizvoda(proizvod.getSlikaProizvoda());
            proizvodDTO.setCena(proizvod.getCena());
            proizvodDTO.setTipProdaje(proizvod.getTipProdaje());
            Set< Kategorija> kategorije=proizvod.getKategorija();
            Set<KategorijaDTO> kategorijeDTO=new HashSet<>();
            for(Kategorija kategorija: kategorije){
                KategorijaDTO kategorijaDTO= new KategorijaDTO();
                kategorijaDTO.setNazivKategorije(kategorija.getNazivKategorije());
                kategorijaDTO.setId(kategorija.getId());
                kategorijeDTO.add(kategorijaDTO);
            }

            proizvodDTO.setKategorije(kategorijeDTO);
            proizvodiDTO.add(proizvodDTO);
        }
        if (proizvodiDTO.isEmpty()) {

            throw new ProductNotFoundException("Trazeni proizvod ne postoji.");
        }
        return proizvodiDTO;
    }

    public List<ProizvodDTO> findByOpis(String description) throws ProductNotFoundException {

        List<Proizvod> proizvodi = proizvodRepository.findByOpis( description);
        List<ProizvodDTO> proizvodiDTO = new ArrayList<>();

        for(Proizvod proizvod: proizvodi){
            ProizvodDTO proizvodDTO = new ProizvodDTO();//ako ovo ne odradim vraacace mi npr dve serpe umesto tiganj i serpu, moram
            proizvodDTO.setId(proizvod.getId());
            proizvodDTO.setNaziv(proizvod.getNaziv());
            proizvodDTO.setOpis(proizvod.getOpis());
            proizvodDTO.setCena(proizvod.getCena());
            proizvodDTO.setTipProdaje(proizvod.getTipProdaje());
            proizvodDTO.setSlikaProizvoda(proizvod.getSlikaProizvoda());
            Set< Kategorija> kategorije=proizvod.getKategorija();
            Set<KategorijaDTO> kategorijeDTO=new HashSet<>();
            for(Kategorija kategorija: kategorije){
                KategorijaDTO kategorijaDTO= new KategorijaDTO();
                kategorijaDTO.setNazivKategorije(kategorija.getNazivKategorije());
                kategorijaDTO.setId(kategorija.getId());
                kategorijeDTO.add(kategorijaDTO);
            }

            proizvodDTO.setKategorije(kategorijeDTO);
            proizvodiDTO.add(proizvodDTO);
        }
        if (proizvodiDTO.isEmpty()) {

            throw new ProductNotFoundException("Trazeni proizvod ne postoji.");
        }
        return proizvodiDTO;

    }

    public List<ProizvodDTO> findByKategorija(String category) throws ProductNotFoundException {

        List<Proizvod> proizvodi = proizvodRepository.findByKategorijaNazivKategorije(category);
        List<ProizvodDTO> proizvodiDTO = new ArrayList<>();

        for(Proizvod proizvod: proizvodi){
            ProizvodDTO proizvodDTO = new ProizvodDTO();//ako ovo ne odradim vraacace mi npr dve serpe umesto tiganj i serpu, moram
            proizvodDTO.setId(proizvod.getId());
            proizvodDTO.setNaziv(proizvod.getNaziv());
            proizvodDTO.setOpis(proizvod.getOpis());
            proizvodDTO.setSlikaProizvoda(proizvod.getSlikaProizvoda());
            proizvodDTO.setCena(proizvod.getCena());
            proizvodDTO.setTipProdaje(proizvod.getTipProdaje());
            Set< Kategorija> kategorije=proizvod.getKategorija();
            Set<KategorijaDTO> kategorijeDTO=new HashSet<>();
            for(Kategorija kategorija: kategorije){
                KategorijaDTO kategorijaDTO= new KategorijaDTO();
                kategorijaDTO.setNazivKategorije(kategorija.getNazivKategorije());
                kategorijaDTO.setId(kategorija.getId());
                kategorijeDTO.add(kategorijaDTO);
            }

            proizvodDTO.setKategorije(kategorijeDTO);
            proizvodiDTO.add(proizvodDTO);
        }
        if (proizvodiDTO.isEmpty()) {

            throw new ProductNotFoundException("Trazeni proizvod ne postoji.");
        }
        return proizvodiDTO;
    }

    public List<ProizvodDTO> findByTipProdaje(TipProdaje type) throws ProductNotFoundException {

        List<Proizvod> proizvodi = proizvodRepository.findByTipProdaje(type);
        List<ProizvodDTO> proizvodiDTO = new ArrayList<>();

        for(Proizvod proizvod: proizvodi){
            ProizvodDTO proizvodDTO = new ProizvodDTO();//ako ovo ne odradim vraacace mi npr dve serpe umesto tiganj i serpu, moram
            proizvodDTO.setId(proizvod.getId());
            proizvodDTO.setNaziv(proizvod.getNaziv());
            proizvodDTO.setOpis(proizvod.getOpis());
            proizvodDTO.setSlikaProizvoda(proizvod.getSlikaProizvoda());
            proizvodDTO.setCena(proizvod.getCena());
            proizvodDTO.setTipProdaje(proizvod.getTipProdaje());
            Set< Kategorija> kategorije=proizvod.getKategorija();
            Set<KategorijaDTO> kategorijeDTO=new HashSet<>();
            for(Kategorija kategorija: kategorije){
                KategorijaDTO kategorijaDTO= new KategorijaDTO();
                kategorijaDTO.setNazivKategorije(kategorija.getNazivKategorije());
                kategorijaDTO.setId(kategorija.getId());
                kategorijeDTO.add(kategorijaDTO);
            }

            proizvodDTO.setKategorije(kategorijeDTO);
            proizvodiDTO.add(proizvodDTO);
        }
        if (proizvodiDTO.isEmpty()) {

            throw new ProductNotFoundException("Trazeni proizvod ne postoji.");
        }
        return proizvodiDTO;
    }

    public List<ProizvodDTO> findByCena(Double priceFrom, Double priceTo) throws ProductNotFoundException {

        List<Proizvod> proizvodi = proizvodRepository.findByCenaGreaterThanEqualAndCenaLessThanEqual(priceFrom, priceTo);
        List<ProizvodDTO> proizvodiDTO = new ArrayList<>();

        for(Proizvod proizvod: proizvodi){
            ProizvodDTO proizvodDTO = new ProizvodDTO();
            proizvodDTO.setId(proizvod.getId());
            proizvodDTO.setNaziv(proizvod.getNaziv());
            proizvodDTO.setOpis(proizvod.getOpis());
            proizvodDTO.setSlikaProizvoda(proizvod.getSlikaProizvoda());
            proizvodDTO.setCena(proizvod.getCena());
            proizvodDTO.setTipProdaje(proizvod.getTipProdaje());
            Set< Kategorija> kategorije=proizvod.getKategorija();
            Set<KategorijaDTO> kategorijeDTO=new HashSet<>();
            for(Kategorija kategorija: kategorije){
                KategorijaDTO kategorijaDTO= new KategorijaDTO();
                kategorijaDTO.setNazivKategorije(kategorija.getNazivKategorije());
                kategorijaDTO.setId(kategorija.getId());
                kategorijeDTO.add(kategorijaDTO);
            }

            proizvodDTO.setKategorije(kategorijeDTO);
            proizvodiDTO.add(proizvodDTO);
        }
        if (proizvodiDTO.isEmpty()) {

            throw new ProductNotFoundException("Trazeni proizvod ne postoji.");
        }
        return proizvodiDTO;
    }
}
