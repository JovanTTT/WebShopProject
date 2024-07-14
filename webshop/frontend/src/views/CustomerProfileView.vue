<template>
  <div class="seller-profile">
    <div class="brand">
      <span class="letter">J</span><span class="letter">O</span><span class="letter">M</span><span class="letter">I</span>
    </div>
    <h1 style="text-align: center;">Profil kupca</h1>

    <div class="seller-image">
      <img v-if="kupac.slika" :src="kupac.slika" alt="Slika profila kupca">
      <p v-else>Profilna slika nije dostupna.</p>
    </div>

    <div class="basic-info">
      <p class="username">{{ kupac.korisnickoIme }}</p>
      <p><strong>Ime:</strong> {{ kupac.ime }} {{ kupac.prezime }}</p>
      <p><strong>Telefon:</strong> {{ kupac.telefon }}</p>
      <p><strong>Opis:</strong> {{ kupac.opisKorisnika }}</p>
    </div>

    <div class="products">
      <h2>Kupljeni proizvodi</h2>
      <ul>
        <li v-for="proizvod in kupac.kupljeniProizvodi" :key="proizvod.id">
          {{ proizvod.naziv }}
        </li>
      </ul>
    </div>

    <div class="reviews">
      <h2>Recenzije kupca (prosečna ocena: {{ kupac.prosecnaOcena }})</h2>
      <ul v-if="kupac.dobijeneRecenzije.length > 0">
        <li v-for="recenzija in kupac.dobijeneRecenzije" :key="recenzija.id">
          <p><strong>Ocena:</strong> {{ recenzija.ocena }}</p>
          <p><strong>Komentar:</strong> {{ recenzija.komentar }}</p>
        </li>
      </ul>
      <p v-else>Nema recenzija za ovog kupca.</p>
    </div>

    <button v-if="checkUserType" @click="showRateCustomerModal = true" class="rate-button">Ostavi ocenu</button>


  </div>
</template>

<script setup>

</script>

<style scoped>

</style>