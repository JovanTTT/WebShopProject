package com.example.webshop.DTO;

import com.example.webshop.model.Uloga;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProdavacDTO {

    private String ime;

    private String prezime;

    private String korisnickoIme;

    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "Neispravan format e-pošte")
    private String mejl;

    @Length(max=11, message = "Neispravan broj telefona!")
    private String telefon;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Lozinka mora sadržati barem 8 znakova, barem jedan broj i barem jedan specijalan znak!")
    private String staraLozinka;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Lozinka mora sadržati barem 8 znakova, barem jedan broj i barem jedan specijalan znak!")
    private String novaLozinka;

    private Date datumRodjenja;

    private Uloga uloga;

    private String slika;

    private String opisKorisnika;

}
