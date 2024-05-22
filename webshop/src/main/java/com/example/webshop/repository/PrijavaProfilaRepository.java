package com.example.webshop.repository;

import com.example.webshop.model.PrijavaProfila;
import com.example.webshop.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrijavaProfilaRepository extends JpaRepository<PrijavaProfila, Long> {
    List<PrijavaProfila> findAllByStatusPrijave(Status status);
}
