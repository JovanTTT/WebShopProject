<template>
  <div class="seller-profile">
    <div class="brand">
      <span class="letter">J</span><span class="letter">O</span><span class="letter">M</span><span class="letter">I</span>
    </div>
    <h1 style="text-align: center;">Profil prodavca</h1>

    <!-- Slika prodavca -->
    <div class="seller-image">
      <img v-if="prodavac.slika" :src="prodavac.slika" alt="Slika profila prodavca">
      <p v-else>Profilna slika nije dostupna.</p>
    </div>

    <!-- Osnovne informacije o prodavcu -->
    <div class="basic-info">
      <p class="username">{{ prodavac.korisnickoIme }}</p>
      <p><strong>Ime:</strong> {{ prodavac.ime }} {{ prodavac.prezime }}</p>
      <p><strong>Telefon:</strong> {{ prodavac.telefon }}</p>
      <p><strong>Opis:</strong> {{ prodavac.opisKorisnika }}</p>
    </div>

    <!-- Proizvodi koje prodavac prodaje -->
    <div class="products">
      <h2>Proizvodi koje prodavac prodaje</h2>
      <ul>
        <li v-for="proizvod in prodavac.proizvodiNaProdaju" :key="proizvod.id">
          {{ proizvod.naziv }}
        </li>
      </ul>
    </div>

    <!-- Recenzije sa prosečnom ocenom -->
    <div class="reviews">
      <h2>Recenzije prodavca (prosečna ocena: {{ prodavac.prosecnaOcena }})</h2>
      <ul v-if="prodavac.dobijeneRecenzije.length > 0">
        <li v-for="recenzija in prodavac.dobijeneRecenzije" :key="recenzija.id">
          <p><strong>Ocena:</strong> {{ recenzija.ocena }}</p>
          <p><strong>Komentar:</strong> {{ recenzija.komentar }}</p>
        </li>
      </ul>
      <p v-else>Nema recenzija za ovog prodavca.</p>
    </div>
    <!-- Dugme za ostavljanje ocene -->
    <button v-if="checkUserType" @click="showRateSellerModal = true" class="rate-button">Ostavi ocenu</button>


  </div>
</template>

<script setup>

</script>

<style scoped>

</style>