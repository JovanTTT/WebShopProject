package com.example.webshop.repository;

import com.example.webshop.model.Ponuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PonudaRepository extends JpaRepository<Ponuda, Long> {

    List<Ponuda> findByProizvodId(Long id);
}
