package com.example.webshop.controller;

import com.example.webshop.DTO.IshodPrijaveDTO;
import com.example.webshop.DTO.PrijavaProfilaDTO;
import com.example.webshop.DTO.PrijavaRequestDTO;
import com.example.webshop.error.*;
import com.example.webshop.model.Korisnik;
import com.example.webshop.model.PrijavaProfila;
import com.example.webshop.model.Status;
import com.example.webshop.model.Uloga;
import com.example.webshop.repository.PrijavaProfilaRepository;
import com.example.webshop.service.KorisnikService;
import com.example.webshop.service.PrijavaProfilaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/report")
public class PrijavaController {
    @Autowired
    private PrijavaProfilaService prijavaProfilaService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private PrijavaProfilaRepository prijavaProfilaRepository;


    @PostMapping("/sellerRequest/{id}")
    public ResponseEntity<?> ProdavacpodnosiPrijavu(@PathVariable Long id, @RequestBody PrijavaRequestDTO prijavaRequestDTO, HttpSession session) throws UserNotFoundException, NoSellerException, NoCustomerException {

        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null){

            throw new UserNotFoundException("Niste prijavljeni!");

        }
        if(!korisnik.getUloga().equals(Uloga.PRODAVAC)){

            throw new NoSellerException("Korisnik koji nije prodavac, ne može da prijavi kupca.");

        }
        if(!korisnikService.jeKupacKupioOdProdavca(id, korisnik.getId())) {
            throw new UserNotFoundException("Prodavac može da da recenziju onom kupcu, koji je od njega kupio proizvod.");
        }


        Optional<Korisnik> korisnikOptional = korisnikService.findById(id);
        if (korisnikOptional.isEmpty()) {
            throw new UserNotFoundException("Korisnik nije pronađen.");
        }
        if (korisnikOptional.get().getUloga().equals(Uloga.KUPAC)) {

            PrijavaProfilaDTO prijavljen = prijavaProfilaService.PodnesiPrijacuZaKupca(korisnik,prijavaRequestDTO, id);

            return ResponseEntity.ok(prijavljen);
        }
        else {
            throw new UserNotFoundException("Možete prijaviti samo kupce vaših proizvoda.");
        }
    }

    @PostMapping("/customerRequest/{id}")
    public ResponseEntity<?> KupacPodnosiPrijavu(@PathVariable Long id, @RequestBody PrijavaRequestDTO prijavaRequestDTO, HttpSession session) throws UserNotFoundException, NoSellerException {

        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
        if(korisnik == null){

            throw new UserNotFoundException("Niste prijavljeni.");

        }
        if(!korisnik.getUloga().equals(Uloga.KUPAC)){

            throw new NoSellerException("Korisnik koji nije kupac, ne može da prijavi prodavca.");

        }
        if(!korisnikService.jeKupacKupioOdProdavca(korisnik.getId(), id)) {
            throw new UserNotFoundException("Kupac može da prijavi onog prodavca od kog je kupio proizvod");
        }


        Optional<Korisnik> korisnikOptional = korisnikService.findById(id);

        if (korisnikOptional.isEmpty()) {
            throw new UserNotFoundException("Korisnik nije pronađen.");
        }
        if (korisnikOptional.get().getUloga().equals(Uloga.PRODAVAC)) {

            PrijavaProfilaDTO prijavljen = prijavaProfilaService.PodnesiPrijacuZaProdavca(korisnik,prijavaRequestDTO, id);

            return ResponseEntity.ok(prijavljen);
        }
        else {
            throw new UserNotFoundException("Možete prijaviti samo prodavce.");
        }
    }

    @PostMapping("/adminRejectionReport/{prijavaId}")
    public ResponseEntity<String> odbijPrijavu(@PathVariable Long prijavaId, @RequestBody IshodPrijaveDTO razlogOdbijanjaDTO, HttpSession session) throws IOException {

        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null) {
            throw new UserNotFoundException("Morate se ulogovati.");
        }

        if (!korisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            throw new NoAdministratorException("Samo admin može odbiti prijave.");
        }
        Optional<PrijavaProfila> prijavaProfila=prijavaProfilaRepository.findById(prijavaId);
        if(prijavaProfila.isEmpty()){
            throw new NoReportException("Prijava ne postoji.");
        }
        if(prijavaProfila.get().getStatusPrijave()!= Status.PODNETA){
            throw new NoReportException("Prijava je već obrađena.");
        }

        prijavaProfilaService.odbijPrijavu(prijavaId, razlogOdbijanjaDTO.getRazlog());
        return ResponseEntity.ok("Prijava je odbijena.");
    }
}
