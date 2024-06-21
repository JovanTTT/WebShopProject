package com.example.webshop.service;

import com.example.webshop.DTO.*;
import com.example.webshop.error.*;
import com.example.webshop.model.*;
import com.example.webshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private ProdavacRepository prodavacRepository;
    @Autowired
    private ProizvodRepository proizvodRepository;
    @Autowired
    private RecenzijaRepository recenzijaRepository;
    @Autowired
    private KupacRepository kupacRepository;

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

    public Korisnik prijava(String korisnickoIme, String lozinka) {

        Korisnik korisnik = korisnikRepository.getByKorisnickoIme(korisnickoIme);
        if(korisnik == null || !korisnik.getLozinka().equals(lozinka))
            return null;
        return  korisnik;

    }

    public Optional<Korisnik> findById(Long id) throws UserNotFoundException {

        Optional<Korisnik> korisnik = korisnikRepository.findById(id);
        if (korisnik.isPresent()) {

            return korisnik;
        } else {

            throw new UserNotFoundException("Korisnik sa ID-jem " + id + " nije pronađen.");
        }
    }

    public Optional<Korisnik> findByKorisnickoIme(String korisnickoIme) throws UserNotFoundException {

        Optional<Korisnik> korisnik = Optional.ofNullable(korisnikRepository.getByKorisnickoIme(korisnickoIme));
        if (korisnik.isPresent()) {

            return korisnik;
        } else {

            throw new UserNotFoundException("Korisnik sa korisnickim imenom: " + korisnickoIme + " nije pronađen.");
        }
    }

    public void updateSeller(Korisnik korisnik, KupacDTO updatedSeller) throws PasswordMismatchException {

        korisnik.setIme(updatedSeller.getIme());
        korisnik.setPrezime(updatedSeller.getPrezime());
        korisnik.setTelefon(updatedSeller.getTelefon());
        korisnik.setUloga(updatedSeller.getUloga());
        korisnik.setDatumRodjenja(updatedSeller.getDatumRodjenja());
        korisnik.setSlika(updatedSeller.getSlika());
        korisnik.setOpisKorisnika(updatedSeller.getOpisKorisnika());

        if(korisnik.getLozinka().equals(updatedSeller.getStaraLozinka())){
            korisnik.setMejl(updatedSeller.getMejl());
            korisnik.setKorisnickoIme(updatedSeller.getKorisnickoIme());
            korisnik.setLozinka(updatedSeller.getNovaLozinka());
        }
        else{
            throw new PasswordMismatchException("Morate uneti ispravnu lozinku.");
        }

        korisnikRepository.save(korisnik);
    }

    public ProdavacProfilDTO proveraProdavac(String korisnickoIme) throws UserNotFoundException {

        Optional<Prodavac> korisnik = prodavacRepository.findByKorisnickoIme(korisnickoIme);
        if (korisnik.isPresent()) {

            ProdavacProfilDTO pp = new ProdavacProfilDTO();
            pp.setIme(korisnik.get().getIme());
            pp.setTelefon(korisnik.get().getTelefon());
            pp.setKorisnickoIme(korisnik.get().getKorisnickoIme());
            pp.setSlika(korisnik.get().getSlika());
            pp.setOpisKorisnika(korisnik.get().getOpisKorisnika());
            pp.setUloga(korisnik.get().getUloga());
            pp.setBlokiran(korisnik.get().getBlokiran());
            pp.setDobijeneRecenzije(korisnik.get().getDobijeneRecenzije());
            pp.setPrezime(korisnik.get().getPrezime());
            pp.setProsecnaOcena(korisnik.get().getProsecnaOcena());
            return pp;
        }else{

            throw new UserNotFoundException("Korisnik sa korisnickim imenom: " + korisnickoIme + " ne postoji.");
        }
    }

    public void updateCustomer(Korisnik korisnik, ProdavacDTO updatedCustomer) throws PasswordMismatchException {

        korisnik.setIme(updatedCustomer.getIme());
        korisnik.setPrezime(updatedCustomer.getPrezime());
        korisnik.setTelefon(updatedCustomer.getTelefon());
        korisnik.setUloga(updatedCustomer.getUloga());
        korisnik.setDatumRodjenja(updatedCustomer.getDatumRodjenja());
        korisnik.setSlika(updatedCustomer.getSlika());
        korisnik.setOpisKorisnika(updatedCustomer.getOpisKorisnika());

        if(korisnik.getLozinka().equals(updatedCustomer.getStaraLozinka())){

            korisnik.setMejl(updatedCustomer.getMejl());
            korisnik.setKorisnickoIme(updatedCustomer.getKorisnickoIme());
            korisnik.setLozinka(updatedCustomer.getNovaLozinka());
        }
        else{

            throw new PasswordMismatchException("Morate uneti ispravnu lozinku.");
        }

        korisnikRepository.save(korisnik);
    }

    public ProdavacProfilDTO getProdavacProfile(Long id) throws UserNotFoundException {

        Optional<Korisnik> korisnikOptional = korisnikRepository.findById(id);

        if (korisnikOptional.isPresent()) {

            Korisnik korisnik = korisnikOptional.get();

            if (korisnik.getUloga().equals(Uloga.PRODAVAC)) {

                Prodavac prodavac = (Prodavac) korisnik;
                Set<ProizvodiNaProdajuDTO> proizvodiNaProdajuDTO=new HashSet<>();

                for(Proizvod p: prodavac.getProizvodiNaProdaju()){
                    ProizvodiNaProdajuDTO proizvodNaProdajuDTO=new ProizvodiNaProdajuDTO();
                    proizvodNaProdajuDTO.setCena(p.getCena());
                    proizvodNaProdajuDTO.setNaziv(p.getNaziv());
                    proizvodNaProdajuDTO.setSlikaProizvoda(p.getSlikaProizvoda());
                    proizvodNaProdajuDTO.setOpis(p.getOpis());
                    proizvodiNaProdajuDTO.add(proizvodNaProdajuDTO);
                }
                ProdavacProfilDTO prodavacProfilDTO = new ProdavacProfilDTO();
                prodavacProfilDTO.setIme(korisnik.getIme());
                prodavacProfilDTO.setPrezime(korisnik.getPrezime());
                prodavacProfilDTO.setKorisnickoIme(korisnik.getKorisnickoIme());
                prodavacProfilDTO.setTelefon(korisnik.getTelefon());
                prodavacProfilDTO.setSlika(korisnik.getSlika());
                prodavacProfilDTO.setOpisKorisnika(korisnik.getOpisKorisnika());
                prodavacProfilDTO.setUloga(korisnik.getUloga());
                prodavacProfilDTO.setBlokiran(korisnik.getBlokiran());
                prodavacProfilDTO.setProizvodiNaProdaju(proizvodiNaProdajuDTO);
                prodavacProfilDTO.setDobijeneRecenzije(korisnikOptional.get().getDobijeneRecenzije());
                prodavacProfilDTO.setProsecnaOcena(prodavac.getProsecnaOcena());


                return  prodavacProfilDTO;
            } else {

                throw new UserNotFoundException("Korisnik sa datim ID-om nije prodavac " + id);
            }
        } else {

            throw new UserNotFoundException("Korisnik sa datim ID-om nije pronađen: " + id);
        }
    }

    public KupacProfilDTO getKupacProfile(Long id) throws UserNotFoundException {

        Optional<Korisnik> korisnikOptional = korisnikRepository.findById(id);

        if (korisnikOptional.isPresent()) {

            Korisnik korisnik = korisnikOptional.get();

            if (korisnik.getUloga().equals(Uloga.KUPAC)) {

                Kupac kupac = (Kupac) korisnik;

                Set<ProizvodiNaProdajuDTO> proizvodiNaProdajuDTO=new HashSet<>();

                for(Proizvod p: kupac.getKupljeniProizvodi()){
                    ProizvodiNaProdajuDTO proizvodNaProdajuDTO=new ProizvodiNaProdajuDTO();
                    proizvodNaProdajuDTO.setCena(p.getCena());
                    proizvodNaProdajuDTO.setNaziv(p.getNaziv());
                    proizvodNaProdajuDTO.setSlikaProizvoda(p.getSlikaProizvoda());
                    proizvodNaProdajuDTO.setOpis(p.getOpis());
                    proizvodiNaProdajuDTO.add(proizvodNaProdajuDTO);
                }
                KupacProfilDTO kupacProfilDTO = new KupacProfilDTO();
                kupacProfilDTO.setIme(korisnik.getIme());
                kupacProfilDTO.setPrezime(korisnik.getPrezime());
                kupacProfilDTO.setKorisnickoIme(korisnik.getKorisnickoIme());
                kupacProfilDTO.setSlika(korisnik.getSlika());
                kupacProfilDTO.setOpisKorisnika(korisnik.getOpisKorisnika());
                kupacProfilDTO.setDatumRodjenja(korisnik.getDatumRodjenja());

                Double prosecnaOcena = kupac.getProsecnaOcena();
                kupacProfilDTO.setProsecnaOcena(prosecnaOcena);

                kupacProfilDTO.setKupljeniProizvodi(proizvodiNaProdajuDTO);

                kupacProfilDTO.setDobijeneRecenzije(korisnikOptional.get().getDobijeneRecenzije());

                return kupacProfilDTO;
            } else {

                throw new UserNotFoundException("Korisnik sa datim ID-om nije kupac: " + id);
            }
        } else {

            throw new UserNotFoundException("Korisnik sa datim ID-om nije pronađen: " + id);
        }
    }

    public boolean jeKupacKupioOdProdavca(Long kupacId, Long prodavacId) {

        List<Proizvod> proizvodi = proizvodRepository.findAllByKupacIdAndProdavacId(kupacId, prodavacId);
        return !proizvodi.isEmpty();
    }

    public ProdavacOceneDTO oceniProdavca(Long kupacId, Long prodavacId, int ocena, String komentar) {

        Prodavac prodavac = prodavacRepository.findById(prodavacId).get();

        String kupacKorisnickoIme = korisnikRepository.findById(kupacId).map(Korisnik::getKorisnickoIme).orElse(null);

        prodavac.getOcene().put(kupacKorisnickoIme, ocena);
        prodavac.getKomentari().put(kupacKorisnickoIme, komentar);

        double prosecnaOcena = prodavac.getOcene().values().stream().mapToInt(Integer::intValue).average().orElse(prodavac.getProsecnaOcena());
        prodavac.setProsecnaOcena(prosecnaOcena);
        prodavac = prodavacRepository.save(prodavac);

        Date d = new Date();

        Korisnik k = korisnikRepository.getByKorisnickoIme(kupacKorisnickoIme);
        Recenzija recenzija = new Recenzija();
        recenzija.setKorisnikKojiJeDaoRecenziju(k);
        recenzija.setKorisnikKojiJeDobioRecenziju(prodavac);
        recenzija.setDatumRecenzije(d);
        recenzija.setOcena(ocena);
        recenzija.setKomentar(komentar);
        recenzijaRepository.save(recenzija);

        ProdavacOceneDTO prodavacDTO = new ProdavacOceneDTO();
        prodavacDTO.setIme(prodavac.getIme());
        prodavacDTO.setPrezime(prodavac.getPrezime());
        prodavacDTO.setKorisnickoIme(prodavac.getKorisnickoIme());
        prodavacDTO.setSlika(prodavac.getSlika());
        prodavacDTO.setOcene(prodavac.getOcene());
        prodavacDTO.setKomentari(prodavac.getKomentari());
        prodavacDTO.setProsecnaOcena(prodavac.getProsecnaOcena());

        return prodavacDTO;
    }

    public double izracunajProsecnuOcenu(Long prodavacId) {

        Prodavac prodavac = prodavacRepository.findById(prodavacId).get();
        return prodavac.getOcene().values().stream().mapToInt(Integer::intValue).average().orElse(prodavac.getProsecnaOcena());
    }

    public boolean jeProdavacProdaoKupcu(Long prodavacId, Long kupacId) {

        List<Proizvod> proizvodi = proizvodRepository.findAllByProdavacIdAndKupacId(prodavacId, kupacId);
        return !proizvodi.isEmpty();
    }

    public KupacOcenaDTO oceniKupca(Long prodavacId, Long kupacId, int ocena, String komentar) {

        Kupac kupac = kupacRepository.findById(kupacId).get();


        String prodavacKorisnickoIme = korisnikRepository.findById(prodavacId).map(Korisnik::getKorisnickoIme).orElse(null);

        kupac.getOcene().put(prodavacKorisnickoIme, ocena);
        kupac.getKomentari().put(prodavacKorisnickoIme, komentar);

        double prosecnaOcena = kupac.getOcene().values().stream().mapToInt(Integer::intValue).average().orElse(kupac.getProsecnaOcena());
        kupac.setProsecnaOcena(prosecnaOcena);
        kupac = kupacRepository.save(kupac);


        Date d = new Date();


        Prodavac p = prodavacRepository.findProdavacByKorisnickoIme(prodavacKorisnickoIme);
        Recenzija recenzija = new Recenzija();
        recenzija.setKorisnikKojiJeDaoRecenziju(p);
        recenzija.setKorisnikKojiJeDobioRecenziju(kupac);
        recenzija.setDatumRecenzije(d);
        recenzija.setOcena(ocena);
        recenzija.setKomentar(komentar);
        recenzijaRepository.save(recenzija);


        KupacOcenaDTO kupacDTO = new KupacOcenaDTO();
        kupacDTO.setIme(kupac.getIme());
        kupacDTO.setPrezime(kupac.getPrezime());
        kupacDTO.setKorisnickoIme(kupac.getKorisnickoIme());
        kupacDTO.setSlika(kupac.getSlika());
        kupacDTO.setOcene(kupac.getOcene());
        kupacDTO.setKomentari(kupac.getKomentari());
        kupacDTO.setProsecnaOcena(kupac.getProsecnaOcena());

        return kupacDTO;
    }

    public double izracunajProsecnuOcenuKupca(Long kupacId) {
        // izračunaj prosečnu ocenu za kupca
        Kupac kupac = kupacRepository.findById(kupacId).get();

        return kupac.getOcene().values().stream().mapToInt(Integer::intValue).average().orElse(kupac.getProsecnaOcena());
    }

    public List<RecenzijaPrikazDTO> vratiRecenzijeKupac(Long kupacId){
        Kupac kupac = kupacRepository.findById(kupacId).get();
        List<Recenzija> sveRecenzije = recenzijaRepository.findAllBykorisnikKojiJeDaoRecenziju(kupac);

        List<RecenzijaPrikazDTO> recenzije = new ArrayList<>();
        for(Recenzija recenzija : sveRecenzije) {
            RecenzijaPrikazDTO dto = new RecenzijaPrikazDTO();
            Korisnik temp = recenzija.getKorisnikKojiJeDobioRecenziju();
            dto.setOcena(recenzija.getOcena());
            dto.setKomentar(recenzija.getKomentar());
            dto.setDatumPodnosenjaRecenzije(recenzija.getDatumRecenzije());

            Korisnik prodavac = recenzija.getKorisnikKojiJeDobioRecenziju();

            ProdavacPrikazRecenzijeDTO prodavacDto = new ProdavacPrikazRecenzijeDTO();
            prodavacDto.setIme(prodavac.getIme());
            prodavacDto.setPrezime(prodavac.getPrezime());
            prodavacDto.setKorisnickoIme(prodavac.getKorisnickoIme());

            dto.setProdavacKojemSamDaoRecenziju(prodavacDto);

            recenzije.add(dto);
        }

        return recenzije;

    }

    public List<RecenzijaPrikazDTO> vratiRecenzijeOdProdavacaKojimaJeKupacDaoRecenziju(Long kupacId){
        Kupac kupac = kupacRepository.findById(kupacId).get();
        List<Recenzija> sveRecenzije = recenzijaRepository.findAllBykorisnikKojiJeDaoRecenziju(kupac);

        List<RecenzijaPrikazDTO> recenzije = new ArrayList<>();
        for(Recenzija recenzija : sveRecenzije) {
            RecenzijaPrikazDTO dto = new RecenzijaPrikazDTO();
            Korisnik prodavac = recenzija.getKorisnikKojiJeDobioRecenziju();

            // Provera da li je prodavac ostavio recenziju kupcu
            List<Recenzija> recenzijeProdavca = recenzijaRepository.findAllBykorisnikKojiJeDaoRecenziju(prodavac);
            boolean prodavacJeOstavioRecenziju = recenzijeProdavca.stream().anyMatch(r -> r.getKorisnikKojiJeDobioRecenziju().equals(kupac));

            if(prodavacJeOstavioRecenziju) {
                dto.setOcena(recenzija.getOcena());
                dto.setKomentar(recenzija.getKomentar());
                dto.setDatumPodnosenjaRecenzije(recenzija.getDatumRecenzije());

                ProdavacPrikazRecenzijeDTO prodavacDto = new ProdavacPrikazRecenzijeDTO();
                prodavacDto.setIme(prodavac.getIme());
                prodavacDto.setPrezime(prodavac.getPrezime());
                prodavacDto.setKorisnickoIme(prodavac.getKorisnickoIme());

                dto.setProdavacKojemSamDaoRecenziju(prodavacDto);

                recenzije.add(dto);
            }
        }

        return recenzije;
    }

    public List<RecenzijaPrikaz2DTO> vratiRecenzijeProdavac(Long prodavacId){
        Prodavac prodavac = prodavacRepository.findById(prodavacId).get();
        List<Recenzija> sveRecenzije = recenzijaRepository.findAllBykorisnikKojiJeDaoRecenziju(prodavac);

        List<RecenzijaPrikaz2DTO> recenzije = new ArrayList<>();
        for(Recenzija recenzija : sveRecenzije) {
            RecenzijaPrikaz2DTO dto = new RecenzijaPrikaz2DTO();
            Korisnik temp = recenzija.getKorisnikKojiJeDobioRecenziju();
            dto.setOcena(recenzija.getOcena());
            dto.setKomentar(recenzija.getKomentar());
            dto.setDatumPodnosenjaRecenzije(recenzija.getDatumRecenzije());

            Korisnik kupac = recenzija.getKorisnikKojiJeDobioRecenziju();

            KupacPrikazRecenzijeDTO prodavacDto = new KupacPrikazRecenzijeDTO();
            prodavacDto.setIme(kupac.getIme());
            prodavacDto.setPrezime(kupac.getPrezime());
            prodavacDto.setKorisnickoIme(kupac.getKorisnickoIme());

            dto.setKupacKojemSamDaoRecenziju(prodavacDto);

            recenzije.add(dto);
        }

        return recenzije;

    }

    public List<RecenzijaPrikaz2DTO> vratiRecenzijeOdKupcaAkoJeProdavacDaoRecenziju(Long prodavacId){
        Prodavac prodavac = prodavacRepository.findById(prodavacId).get();
        List<Recenzija> sveRecenzije = recenzijaRepository.findAllBykorisnikKojiJeDaoRecenziju(prodavac);

        List<RecenzijaPrikaz2DTO> recenzije = new ArrayList<>();
        for(Recenzija recenzija : sveRecenzije) {
            RecenzijaPrikaz2DTO dto = new RecenzijaPrikaz2DTO();
            Korisnik kupac = recenzija.getKorisnikKojiJeDobioRecenziju();

            // Provera da li je kupac ostavio recenziju prodavcu
            List<Recenzija> recenzijeKupca = recenzijaRepository.findAllBykorisnikKojiJeDaoRecenziju(kupac);
            boolean kupacJeOstavioRecenziju = recenzijeKupca.stream().anyMatch(r -> r.getKorisnikKojiJeDobioRecenziju().equals(prodavac));

            if(kupacJeOstavioRecenziju) {
                dto.setOcena(recenzija.getOcena());
                dto.setKomentar(recenzija.getKomentar());
                dto.setDatumPodnosenjaRecenzije(recenzija.getDatumRecenzije());

                KupacPrikazRecenzijeDTO kupacDto = new KupacPrikazRecenzijeDTO();
                kupacDto.setIme(kupac.getIme());
                kupacDto.setPrezime(kupac.getPrezime());
                kupacDto.setKorisnickoIme(kupac.getKorisnickoIme());

                dto.setKupacKojemSamDaoRecenziju(kupacDto);

                recenzije.add(dto);
            }
        }

        return recenzije;
    }

    public List<RecenzijaPrikaz3DTO> vratiRecenzijeAdministrator(Long administratorId){
        Korisnik administrator = korisnikRepository.findById(administratorId).get();
        List<Recenzija> sveRecenzije = recenzijaRepository.findAll();

        List<RecenzijaPrikaz3DTO> recenzije = new ArrayList<>();
        for(Recenzija recenzija : sveRecenzije) {
            RecenzijaPrikaz3DTO dto = new RecenzijaPrikaz3DTO();

            dto.setOcena(recenzija.getOcena());
            dto.setKomentar(recenzija.getKomentar());
            dto.setDatumPodnosenjaRecenzije(recenzija.getDatumRecenzije());

            Korisnik kupac = recenzija.getKorisnikKojiJeDaoRecenziju();
            Korisnik prodavac = recenzija.getKorisnikKojiJeDobioRecenziju();

            KupacPrikazRecenzijeDTO kupacDto = new KupacPrikazRecenzijeDTO();
            kupacDto.setIme(kupac.getIme());
            kupacDto.setPrezime(kupac.getPrezime());
            kupacDto.setKorisnickoIme(kupac.getKorisnickoIme());

            ProdavacPrikazRecenzijeDTO prodavacDto = new ProdavacPrikazRecenzijeDTO();
            prodavacDto.setIme(prodavac.getIme());
            prodavacDto.setPrezime(prodavac.getPrezime());
            prodavacDto.setKorisnickoIme(prodavac.getKorisnickoIme());

            dto.setRecenzijuPodneo(kupacDto);
            dto.setRecenzijuPrimio(prodavacDto);

            recenzije.add(dto);
        }

        return recenzije;
    }

    public void deleteReview(Long reviewId) throws UserNotFoundException {
        if(!recenzijaRepository.existsById(reviewId))
            throw new UserNotFoundException("Recenzija koja ima id " + reviewId + " ne postoji.");
        if(recenzijaRepository.existsById(reviewId)) {
            recenzijaRepository.deleteById(reviewId);
            System.out.println("Recenzija sa ID " + reviewId + " je obrisana.");
        } else {
            System.out.println("Recenzija sa ID " + reviewId + " ne postoji.");
        }
    }

    public Recenzija updateReview(Long reviewId, RecenzijaPrikaz3DTO updatedReview) throws ResourceNotFoundException {
        Recenzija existingReview = recenzijaRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Recenzija sa ID " + reviewId + " nije pronađena"));

        existingReview.setOcena(updatedReview.getOcena());
        existingReview.setKomentar(updatedReview.getKomentar());
        // Postavite ostale atribute koje želite da izmenite

        return recenzijaRepository.save(existingReview);
    }
}

