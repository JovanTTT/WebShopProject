
INSERT INTO Korisnik (id,ime, prezime, korisnicko_ime, mejl, telefon, lozinka, datum_rodjenja, slika, opis_korisnika, uloga, blokiran)
VALUES (1000,'Aleksandar', 'Aleksić', 'aleksa92', 'aleksandar@gmail.com', '060123456', 'lozinka123', '1992-05-20 12:56:12', 'https://cdn.britannica.com/38/188638-050-08A5704E/Tom-Hardy-British-2014.jpg', 'ljubazan', 'KUPAC', false);
INSERT INTO Korisnik (id,ime, prezime, korisnicko_ime, mejl, telefon, lozinka, datum_rodjenja, slika, opis_korisnika, uloga, blokiran)
VALUES (2000,'Mila', 'Milic', 'mila12', 'milicasalajic13@gmail.com', '0664444', 'milalala', '2003-05-20 21:01:48', 'https://i.pinimg.com/564x/3e/63/40/3e634064f267e355659d0eb6e7d28c61.jpg', 'iskusan', 'PRODAVAC', false);
INSERT INTO Korisnik (id,ime, prezime, korisnicko_ime, mejl, telefon, lozinka, datum_rodjenja, slika, opis_korisnika, uloga, blokiran)
VALUES (3000,'Petar', 'Markovic', 'peki001', 'milicasalajic13@gmail.com', '062335514', 'password', '2000-08-15 10:17:29', 'https://www.narodnopozoriste.rs/media/images/artists/523/thumb/300x400/sif_0359.jpg', 'neiskusan', 'PRODAVAC', true);
INSERT INTO Korisnik (id,ime, prezime, korisnicko_ime, mejl, telefon, lozinka, datum_rodjenja, slika, opis_korisnika, uloga, blokiran)
VALUES (4000,'Stefan', 'Nikolic', 'Stefa3', 'stefan@gmail.com', '063588966', 'Lozinka1', '2003-11-29 19:39:56', 'slika4.jpg', 'profesionalan', 'ADMINISTRATOR', false);
INSERT INTO Korisnik (id,ime, prezime, korisnicko_ime, mejl, telefon, lozinka, datum_rodjenja, slika, opis_korisnika, uloga, blokiran)
VALUES (5000,'Aleksandar', 'Radojicic', 'aleksandar', 'acara@gmail.com', '065822326', 'snile', '1990-05-17 16:23:47', 'https://luftika.rs/media/2024/04/aleksandar-radojicic.jpg', 'pouzdan', 'KUPAC', false);
INSERT INTO Korisnik (id,ime, prezime, korisnicko_ime, mejl, telefon, lozinka, datum_rodjenja, slika, opis_korisnika, uloga, blokiran)
VALUES (6000,'Darko', 'Lazic', 'dare99', 'darko@gmail.com', '066698235', 'bivsa121', '1991-10-19 08:59:00', 'https://hitfm.rs/wp-content/uploads/sites/3/2022/08/da.jpg', 'iskusan', 'PRODAVAC', false);
INSERT INTO Korisnik (id,ime, prezime, korisnicko_ime, mejl, telefon, lozinka, datum_rodjenja, slika, opis_korisnika, uloga, blokiran)
VALUES (7000,'Sasa', 'Vuksanovic', 'cale', 'salajicm3@gmail.com', '062359487', 'cale123', '1986-03-28 02:21:22', 'https://mmediamreza.com/_pu/0/73540538.png', 'proveren', 'KUPAC', false);

INSERT INTO Recenzija (ocena, komentar, datum_recenzije, korisnik_koji_je_dao_recenziju_id, korisnik_koji_je_dobio_recenziju_id)
VALUES (4, 'zadovoljan', '2021-02-10 19:54:03',7000, 2000);
INSERT INTO Recenzija (ocena, komentar, datum_recenzije, korisnik_koji_je_dao_recenziju_id, korisnik_koji_je_dobio_recenziju_id)
VALUES (5, 'sve preporuke', '2022-03-21 21:39:47',1000, 2000);
INSERT INTO Recenzija (ocena, komentar, datum_recenzije, korisnik_koji_je_dao_recenziju_id, korisnik_koji_je_dobio_recenziju_id)
VALUES (1, 'proizvod stigao u losem stanju', '2022-04-03 17:20:06',1000, 7000);
INSERT INTO Recenzija (ocena, komentar, datum_recenzije, korisnik_koji_je_dao_recenziju_id, korisnik_koji_je_dobio_recenziju_id)
VALUES (4, '10/10', '2022-03-22 13:35:24',1000, 5000);
INSERT INTO Recenzija (ocena, komentar, datum_recenzije, korisnik_koji_je_dao_recenziju_id, korisnik_koji_je_dobio_recenziju_id)
VALUES (2, 'nema pojma', '2021-06-12 19:32:05',2000, 1000);
INSERT INTO Recenzija (ocena, komentar, datum_recenzije, korisnik_koji_je_dao_recenziju_id, korisnik_koji_je_dobio_recenziju_id)
VALUES (4, 'miki maus', '2020-01-08 20:25:56',7000, 1000);
INSERT INTO Recenzija (ocena, komentar, datum_recenzije, korisnik_koji_je_dao_recenziju_id, korisnik_koji_je_dobio_recenziju_id)
VALUES (5, 't reks', '2020-01-08 20:25:56',2000, 7000);
INSERT INTO Recenzija (ocena, komentar, datum_recenzije, korisnik_koji_je_dao_recenziju_id, korisnik_koji_je_dobio_recenziju_id)
VALUES (1, 'akva park', '2020-01-08 20:25:56',2000, 5000);

INSERT INTO prijava_profila(razlog_prijave,razlog_odbijanja, razlog_prihvatanja, datum_podnosenja_prijave, status_prijave, podnosioc_prijave_id, prijavljeni_korisnik_id)
VALUES ('ne postuje dogovor', 'Nije konkretno navedeno sta nije bilo u redu.',NULL,'2023-02-10 18:25:48', 'ODBIJENA', 1000, 3000);
INSERT INTO prijava_profila(razlog_prijave,razlog_odbijanja, razlog_prihvatanja, datum_podnosenja_prijave, status_prijave, podnosioc_prijave_id, prijavljeni_korisnik_id)
VALUES ('prodao mi je pokvaren proizvod', NULL,NULL,'2023-11-13 20:56:41', 'PODNETA', 2000, 5000);
INSERT INTO prijava_profila(razlog_prijave,razlog_odbijanja, razlog_prihvatanja, datum_podnosenja_prijave, status_prijave, podnosioc_prijave_id, prijavljeni_korisnik_id)
VALUES ('prevarant',NULL,'Vise ljudi je izjavilo ovaj komentar', '2023-08-14 10:56:31', 'PODNETA', 1000, 2000);

INSERT INTO Kupac(id, prosecna_ocena) VALUES (1000, 0);
INSERT INTO Kupac(id, prosecna_ocena) VALUES (5000, 0);
INSERT INTO Kupac(id, prosecna_ocena) VALUES (7000, 0);

INSERT INTO Prodavac(id, prosecna_ocena) VALUES(2000, 0);
INSERT INTO Prodavac(id, prosecna_ocena) VALUES(3000, 0);
INSERT INTO Prodavac(id, prosecna_ocena) VALUES(6000, 0);


INSERT INTO Proizvod (cena, prodat, recenzija_kupac, recenzija_prodavac, datum_objavljivanja, id, kupac_id, prodavac_id, naziv,
                      opis, slika_proizvoda, tip_prodaje) VALUES (2200, true, false, false, '2020-06-12 22:03:48', 1000 ,5000, 2000, 'PAPUCE',
                                                                  'Za po kuci', 'https://cdn.pokloni.com/files/26033/Lama-Papuce-Braon-3.jpg', 'AUKCIJA');
INSERT INTO Proizvod (cena, prodat, recenzija_kupac, recenzija_prodavac, datum_objavljivanja, id, prodavac_id, naziv,
                      opis, slika_proizvoda, tip_prodaje) VALUES (300, false, false, true, '2019-08-21 19:20:36', 2000, 3000, 'KNJIGA ALGEBRA',
                                                                  'FTN, prva godina', 'https://imgv2-1-f.scribdassets.com/img/document/429015388/original/a715bda6de/1716624483?v=1', 'AUKCIJA');
INSERT INTO Proizvod (cena, prodat, recenzija_kupac, recenzija_prodavac, datum_objavljivanja, id, kupac_id, prodavac_id, naziv,
                      opis, slika_proizvoda, tip_prodaje) VALUES (4000, true, false, true, '2020-11-28 09:56:24', 3000, 5000, 3000, 'BUŠILICA',
                                                                  'bosch', 'https://www.agromarket.rs/files/watermark/files/images/slike_proizvoda/thumbs_w/056361_w_800_800px.jpg', 'FIKSNA');
INSERT INTO Proizvod (cena, prodat, recenzija_kupac, recenzija_prodavac, datum_objavljivanja, id, prodavac_id, naziv,
                      opis, slika_proizvoda, tip_prodaje) VALUES (40000, false, true, false, '2018-03-30 20:13:44', 4000, 2000, 'POLICA ZA OBUĆU',
                                                                  'Polica je metalna. Kupljena je u emmezeeti. Visokog kvalitetea i veoma ocuvana. Sklopiva je i ne zauzima puno mesta. Idealna za male hodnike. Koriscena godinu dana.', 'https://c.cdnmp.net/920979014/p/l/3/polica-za-obucu-loft-3-nivoa~4293.jpg', 'FIKSNA');
INSERT INTO Proizvod (cena, prodat, recenzija_kupac, recenzija_prodavac, datum_objavljivanja, id, kupac_id, prodavac_id, naziv,
                      opis, slika_proizvoda, tip_prodaje) VALUES (80000, true, false, true, '2019-01-14 16:11:06', 5000, 1000, 6000, 'IPHONE 8',
                                                                  'U odličnom stanju', 'https://cdn.alloallo.media/catalog/product/apple/iphone/iphone-8/iphone-8-gold.jpg', 'FIKSNA');
INSERT INTO Proizvod (cena, prodat, recenzija_kupac, recenzija_prodavac, datum_objavljivanja, id, kupac_id, prodavac_id, naziv,
                      opis, slika_proizvoda, tip_prodaje) VALUES (15000, true, true, false, '2020-06-12 22:03:48', 6000, 5000, 2000, 'NIKE AIR FORCE',
                                                                  'bele, 43 broj', 'https://img.ep-cdn.com/i/500/500/he/hexkpwjaluorsqtimnbv/nike-wmns-air-force-1-shadow-cene.jpg', 'FIKSNA');
INSERT INTO Proizvod (cena, prodat, recenzija_kupac, recenzija_prodavac, datum_objavljivanja, id, kupac_id, prodavac_id, naziv,
                      opis, slika_proizvoda, tip_prodaje) VALUES (90000, true, false, true, '2019-01-14 16:11:06', 7000, 1000, 3000, 'SAMSUNG A50',
                                                                  'samsung A50 crni', 'https://www.tehnomedia.rs/image/59217.jpg?tip=webp&tip_slike=1', 'FIKSNA');


INSERT INTO Ponuda (cena, proizvod_id, kupac_koji_daje_ponudu_id)VALUES (8000, 2000, 1000);
INSERT INTO Ponuda (cena, proizvod_id, kupac_koji_daje_ponudu_id)VALUES (4500, 3000, 7000);
INSERT INTO Ponuda (cena, proizvod_id, kupac_koji_daje_ponudu_id)VALUES (4000, 3000, 5000);
INSERT INTO Ponuda (cena, proizvod_id, kupac_koji_daje_ponudu_id)VALUES (100000, 4000, 1000);
INSERT INTO Ponuda (cena, proizvod_id, kupac_koji_daje_ponudu_id)VALUES (2200, 5000, 5000);


INSERT INTO Kategorija (id, naziv_kategorije) VALUES (1000, 'Telefoni');
INSERT INTO Kategorija (id, naziv_kategorije) VALUES (2000, 'Nameštaj');
INSERT INTO Kategorija (id, naziv_kategorije) VALUES (3000, 'Alat');
INSERT INTO Kategorija (id, naziv_kategorije) VALUES (4000, 'Knjige');
INSERT INTO Kategorija (id, naziv_kategorije) VALUES (5000, 'Obuća');


INSERT INTO Proizvod_kategorija (proizvod_id, kategorija_id) VALUES (5000, 1000);
INSERT INTO Proizvod_kategorija (proizvod_id, kategorija_id) VALUES (7000, 1000);
INSERT INTO Proizvod_kategorija (proizvod_id, kategorija_id) VALUES (4000, 2000);
INSERT INTO Proizvod_kategorija (proizvod_id, kategorija_id) VALUES (3000, 3000);
INSERT INTO Proizvod_kategorija (proizvod_id, kategorija_id) VALUES (2000, 4000);
INSERT INTO Proizvod_kategorija (proizvod_id, kategorija_id) VALUES (1000, 5000);


