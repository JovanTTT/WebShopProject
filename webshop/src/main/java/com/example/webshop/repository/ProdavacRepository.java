package com.example.webshop.repository;

import com.example.webshop.model.Prodavac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdavacRepository extends JpaRepository<Prodavac, String> {
    Optional<Prodavac> findById(Long id);

    Optional<Prodavac> findByKorisnickoIme(String korisnickoIme);

    Prodavac findProdavacByKorisnickoIme(String prodavacKorisnickoIme);
}
