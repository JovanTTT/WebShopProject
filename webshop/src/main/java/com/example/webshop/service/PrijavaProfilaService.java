package com.example.webshop.service;

import com.example.webshop.DTO.PrijavaKorisnikDTO;
import com.example.webshop.DTO.PrijavaProfilaDTO;
import com.example.webshop.DTO.PrijavaRequestDTO;
import com.example.webshop.error.NoCustomerException;
import com.example.webshop.error.NoReportException;
import com.example.webshop.error.NoSellerException;
import com.example.webshop.error.UserNotFoundException;
import com.example.webshop.model.Korisnik;
import com.example.webshop.model.PrijavaProfila;
import com.example.webshop.model.Status;
import com.example.webshop.model.Uloga;
import com.example.webshop.repository.KorisnikRepository;
import com.example.webshop.repository.PrijavaProfilaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PrijavaProfilaService {
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private PrijavaProfilaRepository prijavaProfilaRepository;

    @Transactional
    public PrijavaProfilaDTO PodnesiPrijacuZaKupca(Korisnik korisnik, PrijavaRequestDTO prijavaRequestDTO, Long id) throws UserNotFoundException, NoCustomerException {

        Optional<Korisnik> prijavljeniKorisnikOptional = korisnikRepository.findById(id);
        Optional<Korisnik> podnosiocPrijave=korisnikRepository.findById(korisnik.getId());
        if (prijavljeniKorisnikOptional.isEmpty()) {

            throw new UserNotFoundException("Korisnik nije pronađen.");
        }

        Korisnik prijavljeniKorisnik = prijavljeniKorisnikOptional.get();

        if (!prijavljeniKorisnik.getUloga().equals(Uloga.KUPAC)) {

            throw new NoCustomerException("Možete prijaviti samo kupce vaših proizvoda.");
        }
        PrijavaProfila prijavaProfila = new PrijavaProfila();
        prijavaProfila.setRazlogPrijave(prijavaRequestDTO.getRazlogPrijave());
        prijavaProfila.setDatumPodnosenjaPrijave(new Date());
        prijavaProfila.setStatusPrijave(Status.PODNETA);
        prijavaProfila.setPodnosiocPrijave(podnosiocPrijave.get());
        prijavaProfila.setPrijavljeniKorisnik(prijavljeniKorisnik);

        prijavaProfila = prijavaProfilaRepository.save(prijavaProfila);
        PrijavaKorisnikDTO prijavljenKorisnikDTO=new PrijavaKorisnikDTO();
        PrijavaKorisnikDTO podnosiocPrijaveKorisnikDTO=new PrijavaKorisnikDTO();
        prijavljenKorisnikDTO.setIme(prijavljeniKorisnik.getIme());
        prijavljenKorisnikDTO.setPrezime(prijavljeniKorisnik.getPrezime());
        prijavljenKorisnikDTO.setMejl(prijavljeniKorisnik.getMejl());
        prijavljenKorisnikDTO.setKorisnickoIme(prijavljeniKorisnik.getKorisnickoIme());

        podnosiocPrijaveKorisnikDTO.setKorisnickoIme(podnosiocPrijave.get().getKorisnickoIme());
        podnosiocPrijaveKorisnikDTO.setIme(podnosiocPrijave.get().getIme());
        podnosiocPrijaveKorisnikDTO.setPrezime(podnosiocPrijave.get().getPrezime());
        podnosiocPrijaveKorisnikDTO.setMejl(podnosiocPrijave.get().getMejl());


        PrijavaProfilaDTO prijavaProfilaDTO=new PrijavaProfilaDTO();
        prijavaProfilaDTO.setDatumPodnosenjaPrijave(prijavaProfila.getDatumPodnosenjaPrijave());
        prijavaProfilaDTO.setPrijavljeniKorisnik(prijavljenKorisnikDTO);
        prijavaProfilaDTO.setPodnosiocPrijave(podnosiocPrijaveKorisnikDTO);
        prijavaProfilaDTO.setStatusPrijave(prijavaProfila.getStatusPrijave());
        prijavaProfilaDTO.setRazlogPrijave(prijavaProfila.getRazlogPrijave());


        return prijavaProfilaDTO;
    }

    public List<PrijavaProfila> pregledPrijava() {

        return prijavaProfilaRepository.findAllByStatusPrijave(Status.PODNETA);
    }

    public PrijavaProfilaDTO PodnesiPrijacuZaProdavca(Korisnik korisnik, PrijavaRequestDTO prijavaRequestDTO, Long id) throws UserNotFoundException, NoSellerException {

        Optional<Korisnik> prijavljeniKorisnikOptional = korisnikRepository.findById(id);
        Optional<Korisnik> podnosiocPrijave=korisnikRepository.findById(korisnik.getId());
        if (prijavljeniKorisnikOptional.isEmpty()) {

            throw new UserNotFoundException("Korisnik nije pronađen.");
        }

        Korisnik prijavljeniKorisnik = prijavljeniKorisnikOptional.get();

        if (!prijavljeniKorisnik.getUloga().equals(Uloga.PRODAVAC)) {

            throw new NoSellerException("Možete prijaviti samo prodavca od kog ste uzeli proizvod.");
        }

        PrijavaProfila prijavaProfila = new PrijavaProfila();
        prijavaProfila.setRazlogPrijave(prijavaRequestDTO.getRazlogPrijave());
        prijavaProfila.setDatumPodnosenjaPrijave(new Date());
        prijavaProfila.setStatusPrijave(Status.PODNETA); // ili neki drugi podrazumevani status
        prijavaProfila.setPodnosiocPrijave(podnosiocPrijave.get());
        prijavaProfila.setPrijavljeniKorisnik(prijavljeniKorisnik);


        prijavaProfila = prijavaProfilaRepository.save(prijavaProfila);
        PrijavaKorisnikDTO prijavljenKorisnikDTO=new PrijavaKorisnikDTO();
        PrijavaKorisnikDTO podnosiocPrijaveKorisnikDTO=new PrijavaKorisnikDTO();
        prijavljenKorisnikDTO.setIme(prijavljeniKorisnik.getIme());
        prijavljenKorisnikDTO.setPrezime(prijavljeniKorisnik.getPrezime());
        prijavljenKorisnikDTO.setMejl(prijavljeniKorisnik.getMejl());
        prijavljenKorisnikDTO.setKorisnickoIme(prijavljeniKorisnik.getKorisnickoIme());

        podnosiocPrijaveKorisnikDTO.setKorisnickoIme(podnosiocPrijave.get().getKorisnickoIme());
        podnosiocPrijaveKorisnikDTO.setIme(podnosiocPrijave.get().getIme());
        podnosiocPrijaveKorisnikDTO.setPrezime(podnosiocPrijave.get().getPrezime());
        podnosiocPrijaveKorisnikDTO.setMejl(podnosiocPrijave.get().getMejl());


        PrijavaProfilaDTO prijavaProfilaDTO=new PrijavaProfilaDTO();
        prijavaProfilaDTO.setDatumPodnosenjaPrijave(prijavaProfila.getDatumPodnosenjaPrijave());
        prijavaProfilaDTO.setPrijavljeniKorisnik(prijavljenKorisnikDTO);
        prijavaProfilaDTO.setPodnosiocPrijave(podnosiocPrijaveKorisnikDTO);
        prijavaProfilaDTO.setStatusPrijave(prijavaProfila.getStatusPrijave());
        prijavaProfilaDTO.setRazlogPrijave(prijavaProfila.getRazlogPrijave());

        return prijavaProfilaDTO;
    }

    @Transactional
    public void odbijPrijavu(Long prijavaId, String razlogOdbijanja) throws IOException {

        Optional<PrijavaProfila> prijava = prijavaProfilaRepository.findById(prijavaId);
        if(prijava.isEmpty()){

            throw new NoReportException("Tražena prijava ne postoji");
        }
        //  PrijavaProfila izmenjenaPrijava= new PrijavaProfila();
//        prijava.get().setStatusPrijave(Status.ODBIJENA);
//        prijava.get().setRazlogOdbijanja(razlogOdbijanja);
//        prijavaProfilaRepository.save(prijava.get());
//        Korisnik korisnik=prijava.get().getPodnosiocPrijave();
//        sendReportRejected(korisnik, razlogOdbijanja);
        PrijavaProfila prijavaProfila = prijava.get();
        prijavaProfila.setStatusPrijave(Status.ODBIJENA);
        prijavaProfila.setRazlogOdbijanja(razlogOdbijanja);
        prijavaProfilaRepository.save(prijavaProfila);
        Korisnik korisnik = prijavaProfila.getPodnosiocPrijave();

    }

    @Transactional
    public void prihvatiPrijavu(Long prijavaId, String razlogPrihvatanja) throws IOException {

        Optional<PrijavaProfila> prijava = prijavaProfilaRepository.findById(prijavaId);
        if(prijava.isEmpty()){

            throw new NoReportException("Tražena prijava ne postoji.");
        }

        prijava.get().setStatusPrijave(Status.PRIHVACENA);
        prijava.get().setRazlogOdbijanja(razlogPrihvatanja);
        prijavaProfilaRepository.save(prijava.get());
        Korisnik korisnik=prijava.get().getPodnosiocPrijave();
        Korisnik prijavljeniKorisnik=prijava.get().getPrijavljeniKorisnik();
        prijavljeniKorisnik.setBlokiran(true);

    }

    public List<PrijavaProfilaDTO> vratiPrijaveAdministrator(Long administratorId){
        Korisnik admin = korisnikRepository.findById(administratorId).get();
        List<PrijavaProfila> svePrijave = prijavaProfilaRepository.findAll();

        List<PrijavaProfilaDTO> prijave = new ArrayList<>();
        for(PrijavaProfila prijava : svePrijave){
            PrijavaProfilaDTO dto = new PrijavaProfilaDTO();

            dto.setRazlogPrijave(prijava.getRazlogPrijave());
            dto.setStatusPrijave(prijava.getStatusPrijave());
            dto.setDatumPodnosenjaPrijave(prijava.getDatumPodnosenjaPrijave());
            dto.setId(prijava.getId());

            Korisnik podnosilac = prijava.getPodnosiocPrijave();
            Korisnik prijavljen = prijava.getPrijavljeniKorisnik();

            PrijavaKorisnikDTO podnosilacDto = new PrijavaKorisnikDTO();
            podnosilacDto.setIme(podnosilac.getIme());
            podnosilacDto.setPrezime(podnosilac.getPrezime());
            podnosilacDto.setMejl(podnosilac.getMejl());
            podnosilacDto.setKorisnickoIme(podnosilac.getKorisnickoIme());

            PrijavaKorisnikDTO prijavljenDto = new PrijavaKorisnikDTO();
            prijavljenDto.setIme(prijavljen.getIme());
            prijavljenDto.setPrezime(prijavljen.getPrezime());
            prijavljenDto.setMejl(prijavljen.getMejl());
            prijavljenDto.setKorisnickoIme(prijavljen.getKorisnickoIme());

            dto.setPodnosiocPrijave(podnosilacDto);
            dto.setPrijavljeniKorisnik(prijavljenDto);

            prijave.add(dto);
        }
        return prijave;
    }
}
