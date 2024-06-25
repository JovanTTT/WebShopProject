package com.example.webshop.controller;

import com.example.webshop.DTO.ProizvodDTO;
import com.example.webshop.DTO.SviProizvodiDTO;
import com.example.webshop.error.PasswordMismatchException;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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

    @GetMapping("/search")
    public ResponseEntity<List<ProizvodDTO>> searchProducts(@RequestParam(required = false) String name, @RequestParam(required = false) String description) throws ProductNotFoundException, PasswordMismatchException, PasswordMismatchException {

        List<ProizvodDTO> proizvod = new ArrayList<>();
        if (name != null && description != null) {
            proizvod = proizvodService.findByNazivAndOpis(name, description);

        } else if (name != null) {
            proizvod = proizvodService.findByNaziv(name);

        } else if (description != null) {
            proizvod = proizvodService.findByOpis(description);

        } else {
            proizvod = proizvodService.findAll();
        }
        return ResponseEntity.ok(proizvod);
    }

    @GetMapping("/filterByCategory")
    public ResponseEntity<List<ProizvodDTO>> filterProductsByCategory(@RequestParam(required = false) String category) throws ProductNotFoundException {

        List<ProizvodDTO> proizvod = new ArrayList<>();

        if (category != null ) {
            proizvod = proizvodService.findByKategorija(category);

        }
        else {
            proizvod = proizvodService.findAll();

        }
        return ResponseEntity.ok(proizvod);
    }
}
