package com.example.webshop.controller;

import com.example.webshop.DTO.KupacDTO;
import com.example.webshop.DTO.KupacProfilDTO;
import com.example.webshop.DTO.PrijavaKorisnikaDTO;
import com.example.webshop.DTO.RegistracijaKorisnikaDTO;
import com.example.webshop.error.*;
import com.example.webshop.model.Korisnik;
import com.example.webshop.model.Uloga;
import com.example.webshop.service.KorisnikService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

      @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Korisnik> registracijaKorisnika(@Valid @RequestBody RegistracijaKorisnikaDTO korisnik) throws UserAlreadyExistsException, EmailAlreadyExistsException, PasswordMismatchException {//valid proverava da li su ispunjeni zahtevi unutar registracija korstnika dTO

        Korisnik registrovaniKorisnik = korisnikService.registracijaKorisnika(korisnik);
        return new ResponseEntity<>(registrovaniKorisnik, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> prijava(@RequestBody PrijavaKorisnikaDTO prijavaDto, HttpSession session){

        if(prijavaDto.getKorisnickoIme().isEmpty() || prijavaDto.getLozinka().isEmpty())
            return new ResponseEntity("Neispravni podaci, molim Vas pokušajte ponovo.", HttpStatus.BAD_REQUEST);

        Korisnik loginovaniKorisnik = korisnikService.prijava(prijavaDto.getKorisnickoIme(), prijavaDto.getLozinka());
        if (loginovaniKorisnik == null)
            return new ResponseEntity<>("Korisnik ne postoji.", HttpStatus.NOT_FOUND);

        session.setAttribute("korisnik", loginovaniKorisnik);
        return ResponseEntity.ok("Prijava uspešna.");
    }

    @PostMapping("/logout")
    public ResponseEntity Odjava(HttpSession session){

        Korisnik loggedEmployee = (Korisnik) session.getAttribute("korisnik");

        if (loggedEmployee == null)
            return new ResponseEntity("Došlo je do greške prilikom odjave.", HttpStatus.FORBIDDEN);

        session.invalidate();
        return new ResponseEntity("Odjava uspešna.", HttpStatus.OK);
    }

    @PutMapping("/updateSeller/{id}")
    public ResponseEntity<?> updateSeller(@PathVariable Long id, @RequestBody KupacDTO updatedSeller, HttpSession session) throws PasswordMismatchException, EmailAlreadyExistsException, UserAlreadyExistsException, UserNotFoundException, NoSellerException {

        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null){
            throw new UserNotFoundException("Samo ulogovani korisnici mogu da menjaju podatke.");
        }

        Optional<Korisnik> existingUser = korisnikService.findById(id);

        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("Korisnik sa ID-jem " + id + " nije pronađen.");
        }

        if (!existingUser.get().getKorisnickoIme().equals(korisnik.getKorisnickoIme())) {
            throw new NoSellerException("Ne možete menjati podatke drugima.");
        }

        if(existingUser.get().getUloga() != Uloga.KUPAC){
            throw new NoSellerException("Samo kupac može da menja podatke.");
        }

        korisnikService.updateSeller(existingUser.get(), updatedSeller);
        return ResponseEntity.ok().build();

    }


}

