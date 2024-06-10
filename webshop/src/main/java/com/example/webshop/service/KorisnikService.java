package com.example.webshop.service;

import com.example.webshop.DTO.RegistracijaKorisnikaDTO;
import com.example.webshop.error.EmailAlreadyExistsException;
import com.example.webshop.error.PasswordMismatchException;
import com.example.webshop.error.UserAlreadyExistsException;
import com.example.webshop.model.Korisnik;
import com.example.webshop.model.PrijavaProfila;
import com.example.webshop.model.Recenzija;
import com.example.webshop.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    public boolean emailExsist(String mejl) {
        return korisnikRepository.existsByMejl(mejl);
    }
    public boolean userExsist(String korisnickoIme) {
        return korisnikRepository.existsByKorisnickoIme(korisnickoIme);
    }

    public Korisnik registracijaKorisnika(RegistracijaKorisnikaDTO korisnikDTO) throws UserAlreadyExistsException, EmailAlreadyExistsException, PasswordMismatchException {


        Korisnik korisnik=new Korisnik();
        korisnik.setIme(korisnikDTO.getIme());
        korisnik.setPrezime(korisnikDTO.getPrezime());
        korisnik.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
        korisnik.setLozinka(korisnikDTO.getLozinka());
        korisnik.setTelefon(korisnikDTO.getTelefon());
        korisnik.setUloga(korisnikDTO.getUloga());
        korisnik.setMejl(korisnikDTO.getMejl());
        korisnik.setDatumRodjenja(korisnikDTO.getDatumRodjenja());

        korisnik.setBlokiran(false);
        korisnik.setOpisKorisnika("");

        Set<PrijavaProfila> prijava = new HashSet<>();
        korisnik.setPrijava(prijava);
        Set<PrijavaProfila> prijavljen = new HashSet<>();
        korisnik.setPrijavljen(prijavljen);
        Set<Recenzija> recenzije = new HashSet<>();
        korisnik.setRecenzije(recenzije);

        if(this.emailExsist(korisnikDTO.getMejl())) {

            throw new EmailAlreadyExistsException("Korisnik sa ovim email-om već postoji.");
        }

        if(this.userExsist(korisnikDTO.getKorisnickoIme())){

            throw new UserAlreadyExistsException("Korisnik sa ovim korisnickim imenom vec postoji.");
        }
        if (!korisnikDTO.getLozinka().equals(korisnikDTO.getPonovljenaLozinka())) {

            throw new PasswordMismatchException("Lozinke se ne poklapaju.");
        }
        korisnik=korisnikRepository.save(korisnik);


        return korisnik;
    }
}

