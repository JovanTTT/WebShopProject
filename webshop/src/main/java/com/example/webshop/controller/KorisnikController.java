package com.example.webshop.controller;

import com.example.webshop.DTO.*;
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

    @GetMapping("/profileView/{id}")
    public ResponseEntity<?> getUser(@PathVariable(name = "id") Long id, HttpSession session) throws UserNotFoundException {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null){
            throw new UserNotFoundException("Niste prijavljeni.");
        }

        Optional<Korisnik> korisnikOptional = korisnikService.findById(id);
        if (korisnikOptional.isEmpty()) {
            throw new UserNotFoundException("Korisnik sa datim ID-om nije pronađen.");
        }

        if (korisnikOptional.get().getUloga().equals(Uloga.KUPAC)) {

            KupacProfilDTO kupac = korisnikService.getKupacProfile(id);
            return ResponseEntity.ok(kupac);
        }
        else if (korisnikOptional.get().getUloga().equals(Uloga.PRODAVAC)) {

            ProdavacProfilDTO prodavac = korisnikService.getProdavacProfile(id);
            return ResponseEntity.ok(prodavac);
        }

        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nemate pristup ovom profilu.");
        }
    }

    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody ProdavacDTO updatedCustomer, HttpSession session) throws PasswordMismatchException, EmailAlreadyExistsException, UserAlreadyExistsException, UserNotFoundException, NoSellerException, NoCustomerException {

        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null){
            // return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            throw new UserNotFoundException("Samo ulogovani korisnici mogu da menjaju podatke.");
        }

        Optional<Korisnik> existingUser = korisnikService.findById(id);

        if (existingUser.isEmpty()) {
            // return ResponseEntity.notFound().build();
            throw new UserNotFoundException("Korisnik sa ID-jem " + id + " nije pronađen.");
        }

        if (!existingUser.get().getKorisnickoIme().equals(korisnik.getKorisnickoIme())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if(existingUser.get().getUloga() != Uloga.PRODAVAC){
            throw new NoCustomerException("Samo PRODAVAC moze da menja podatke.");
        }

        korisnikService.updateCustomer(existingUser.get(), updatedCustomer);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/rateSeller/{prodavacId}")
    public ProdavacOceneDTO oceniProdavca(@PathVariable Long prodavacId, @RequestParam int ocena, @RequestParam String komentar, HttpSession session) throws UserNotFoundException {

        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null) {
            throw new UserNotFoundException("Samo ulogovani korisnici mogu da ocenjuju druge korisnike.");
        }

        if (!korisnikService.jeKupacKupioOdProdavca(korisnik.getId(), prodavacId)) {
            throw new UserNotFoundException("Kupac može da oceni prodavca samo ako je kupio proizvod od tog prodavca.");

        }
        return korisnikService.oceniProdavca(korisnik.getId(), prodavacId, ocena, komentar);

    }

    @GetMapping("/averageRatingSeller/{prodavacId}")
    public double prosecnaOcena (@PathVariable Long prodavacId, HttpSession session) throws UserNotFoundException, NoSellerException {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null) {
            throw new UserNotFoundException("Samo ulogovanik korisnici mogu da vide prosecne ocene drugih prodavaca!");
        }

        Optional<Korisnik> prodavac = korisnikService.findById(prodavacId);
        if(prodavac.get().getUloga() != Uloga.PRODAVAC){
            throw  new NoSellerException("Korisnik sa zeljenim ID-jem nije prodavac!");
        }

        return korisnikService.izracunajProsecnuOcenu(prodavacId);
    }

    @PostMapping("/rateBuyer/{kupacId}")
    public KupacOcenaDTO oceniKupca (@PathVariable Long kupacId,@RequestParam int ocena,
                                     @RequestParam String komentar, HttpSession session) throws UserNotFoundException {

        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null) {
            throw new UserNotFoundException("Samo ulogovani korisnici mogu da ocenjuju druge korisnike.");
        }

        if (!korisnikService.jeProdavacProdaoKupcu(korisnik.getId(), kupacId)) {
            throw new UserNotFoundException("Prodavac može da oceni kupca samo ako je prodao proizvod tom kupcu.");
        }

        return korisnikService.oceniKupca(korisnik.getId(), kupacId, ocena, komentar);
    }

    @GetMapping("/averageRatingBuyer/{kupacId}")
    public double prosecnaOcenaKupca (@PathVariable Long kupacId, HttpSession session) throws UserNotFoundException, NoSellerException {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null) {
            throw new UserNotFoundException("Samo ulogovanik korisnici mogu da vide prosecne ocene drugih prodavaca!");
        }

        Optional<Korisnik> kupac = korisnikService.findById(kupacId);
        if(kupac.get().getUloga() != Uloga.KUPAC){
            throw  new NoSellerException("Korisnik sa zeljenim ID-jem nije kupac!");
        }

        return korisnikService.izracunajProsecnuOcenuKupca(kupacId);
    }

    @GetMapping("/reviewsBuyer/sent")
    public ResponseEntity<List<RecenzijaPrikazDTO>> getUserReviewsBuyer (HttpSession session) throws
            UserNotFoundException, NoSellerException {

        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null) {
            throw new UserNotFoundException("Samo ulogovani korisnici mogu da pregledaju recenzije.");
        }

        if (!korisnik.getUloga().equals(Uloga.KUPAC)) {
            throw new NoSellerException("Samo kupac može da pregleda recenzije.");
        }

        List<RecenzijaPrikazDTO> recenzije = korisnikService.vratiRecenzijeKupac(korisnik.getId());
        return new ResponseEntity<>(recenzije, HttpStatus.OK);
    }

    @GetMapping("/reviewedSellers/received")
    public ResponseEntity<List<RecenzijaPrikazDTO>> getReviewsFromReviewedSellers (HttpSession session) throws
            UserNotFoundException, NoSellerException {

        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null) {
            throw new UserNotFoundException("Samo ulogovani korisnici mogu da pregledaju recenzije.");
        }

        if (!korisnik.getUloga().equals(Uloga.KUPAC)) {
            throw new NoSellerException("Samo kupac može da pregleda recenzije.");
        }
        List<RecenzijaPrikazDTO> recenzije = korisnikService.vratiRecenzijeOdProdavacaKojimaJeKupacDaoRecenziju(korisnik.getId());
        return new ResponseEntity<>(recenzije, HttpStatus.OK);
    }

    @GetMapping("/reviewedBuyer/received")
    public ResponseEntity<List<RecenzijaPrikaz2DTO>> getReviewsFromReviewedBuyers (HttpSession session) throws
            UserNotFoundException, NoSellerException {

        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null) {
            throw new UserNotFoundException("Samo ulogovani korisnici mogu da pregledaju recenzije.");
        }

        if (!korisnik.getUloga().equals(Uloga.PRODAVAC)) {
            throw new NoSellerException("Samo prodavac može pregleda recenzije.");
        }
        List<RecenzijaPrikaz2DTO> recenzije = korisnikService.vratiRecenzijeOdKupcaAkoJeProdavacDaoRecenziju(korisnik.getId());
        return new ResponseEntity<>(recenzije, HttpStatus.OK);
    }
}

