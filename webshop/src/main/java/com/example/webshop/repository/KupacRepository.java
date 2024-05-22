package com.example.webshop.repository;

import com.example.webshop.model.Kupac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KupacRepository extends JpaRepository<Kupac, String> {

    Optional<Kupac> findById(Long id);
}
