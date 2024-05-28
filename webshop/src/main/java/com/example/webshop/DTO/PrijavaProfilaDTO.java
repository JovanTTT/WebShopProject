package com.example.webshop.DTO;

import com.example.webshop.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PrijavaProfilaDTO {

    private String razlogPrijave;

    private Date datumPodnosenjaPrijave;

    private Status statusPrijave;

    private PrijavaKorisnikDTO podnosiocPrijave;

    private PrijavaKorisnikDTO prijavljeniKorisnik;

}
