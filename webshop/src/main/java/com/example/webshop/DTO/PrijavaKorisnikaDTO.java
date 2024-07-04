package com.example.webshop.DTO;

import com.example.webshop.model.Uloga;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrijavaKorisnikaDTO {

    private String korisnickoIme;

    private String lozinka;

    private Uloga uloga;

}
