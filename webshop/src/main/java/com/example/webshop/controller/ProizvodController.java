package com.example.webshop.controller;

import com.example.webshop.DTO.ProizvodDTO;
import com.example.webshop.DTO.SviProizvodiDTO;
import com.example.webshop.error.*;
import com.example.webshop.model.Korisnik;
import com.example.webshop.model.Proizvod;
import com.example.webshop.model.TipProdaje;
import com.example.webshop.service.ProizvodService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/filterByType")
    public ResponseEntity<List<ProizvodDTO>> filterProductsByType(@RequestParam(required = false) TipProdaje type) throws ProductNotFoundException {

        List<ProizvodDTO> proizvod = new ArrayList<>();

        if (type != null ) {
            proizvod = proizvodService.findByTipProdaje(type);

        }
        else {
            proizvod = proizvodService.findAll();

        }
        return ResponseEntity.ok(proizvod);
    }

    @GetMapping("/filterByPrice")
    public ResponseEntity<List<ProizvodDTO>> filterProductsByPrice(@RequestParam(required = false) Double priceFrom,@RequestParam(required = false) Double priceTo) throws ProductNotFoundException {

        List<ProizvodDTO> proizvod = new ArrayList<>();

        if (priceFrom!= null && priceTo!=null ) {
            proizvod = proizvodService.findByCena(priceFrom, priceTo);

        }
        else {
            proizvod = proizvodService.findAll();
            throw new ProductNotFoundException("Morate uneti od-do za cenu.");


        }
        return ResponseEntity.ok(proizvod);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Proizvod updatedProduct, HttpSession session) throws UserNotFoundException, ProductCanNotBeeChanged, NoSellerException, UserNotFoundException {

        Korisnik korisnik= (Korisnik) session.getAttribute("korisnik");

        if(korisnik==null){
            System.out.println("uslo u if");
            throw new UserNotFoundException("Morate biti prijavljeni.");

        }

        Optional<Proizvod> existingProduct = proizvodService.findById(id);

        if(existingProduct.get().getProdat()){
            throw new ProductCanNotBeeChanged("Proizvod je prodat.");

        }
        if (existingProduct.isEmpty()) {
            throw new UserNotFoundException("Proizvod sa ID-jem " + id + " nije pronađen.");

        }
        if (existingProduct.get().getTipProdaje() == TipProdaje.AUKCIJA && !existingProduct.get().getPonude().isEmpty()) {

            throw new ProductCanNotBeeChanged("Proizvod se ne može izmeniti jer postoje aktivne ponude u aukciji.");
        }

        if (!existingProduct.get().getProdavac().getKorisnickoIme().equals(korisnik.getKorisnickoIme())) {

            //  return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            throw  new NoSellerException("Niste prodavac ovog proizvoda, ne možete ga izmeniti.");
        }
        SviProizvodiDTO proizvodDTO =proizvodService.updateProduct(existingProduct.get(), updatedProduct);

        // return ResponseEntity.ok().build();
        return ResponseEntity.ok(proizvodDTO);
    }
}
