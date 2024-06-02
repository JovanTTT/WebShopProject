package com.example.webshop.DTO;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecenzijaDTO {

    private double ocena;

    private String komentar;

    private Date datumRecenzije;
}
