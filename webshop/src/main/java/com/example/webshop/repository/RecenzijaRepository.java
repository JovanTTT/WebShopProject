package com.example.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.webshop.model.Recenzija;
import com.example.webshop.model.Korisnik;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecenzijaRepository extends JpaRepository<Recenzija, Long> {
    List<Recenzija> findByKorisnikKojiJeDaoRecenzijuId(Long korisnikId);

    List<Recenzija> findAllBykorisnikKojiJeDaoRecenziju(Korisnik korisnikKojiJeDaoRecenziju);

    Optional<Recenzija> findById(Long id);

    @Query("SELECT AVG(r.ocena) FROM Recenzija r WHERE r.korisnikKojiJeDobioRecenziju.id = :korisnikId")
    Double findAverageRatingByKorisnikId(Long korisnikId);
}
