package com.example.webshop.controller;

import com.example.webshop.DTO.SviProizvodiDTO;
import com.example.webshop.error.ProductNotFoundException;
import com.example.webshop.model.Proizvod;
import com.example.webshop.service.ProizvodService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/product")
public class ProizvodController {

    @Autowired
    private ProizvodService proizvodService;

    @GetMapping("/products")
    public ResponseEntity<List<SviProizvodiDTO>> getAllProducts() {

        List<SviProizvodiDTO> proizvodiDTO = proizvodService.findAllProducts();
        return ResponseEntity.ok(proizvodiDTO);
    }

    @GetMapping("/{id}")
    public SviProizvodiDTO getEmployee(@PathVariable(name = "id") Long id, HttpSession session) throws ProductNotFoundException {

        Proizvod proizvod = (Proizvod) session.getAttribute("proizvod");
        session.invalidate();
        SviProizvodiDTO nadjenProizvod=proizvodService.findProduct(id);
        if(nadjenProizvod==null){
            throw new ProductNotFoundException("Traženi proizvod ne postoji.");
        }
        return proizvodService.findProduct(id);
    }
}
