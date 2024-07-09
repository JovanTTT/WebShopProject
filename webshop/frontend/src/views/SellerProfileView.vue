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

    <!-- Modalni dijalog za ocenjivanje prodavca -->
    <div v-if="showRateSellerModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeRateSellerModal">&times;</span>
        <h2>Oceni prodavca</h2>
        <form @submit.prevent="submitRating">
          <label for="ocena">Ocena:</label>
          <input type="number" id="ocena" v-model="ocenaKupca" min="1" max="5" required>
          <label for="komentar">Komentar:</label>
          <textarea id="komentar" v-model="komentarKupca"></textarea>
          <button type="submit">Pošalji ocenu</button>
        </form>
      </div>
    </div>
    <button v-if="checkUserType" @click="showComplaintModal = true" class="submit-button">Prijavi prodavca</button>
    <!-- Modalni dijalog za prijavu prodavca -->
    <div v-if="showComplaintModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeComplaintModal">&times;</span>
        <h2>Prijavi prodavca</h2>
        <form @submit.prevent="submitComplaint">
          <label for="prijava">Prijava:</label>
          <textarea id="prijava" v-model="prijavaText" required></textarea>
          <button type="submit">Pošalji prijavu</button>
        </form>
      </div>
    </div>
    <!-- Modalni prozor za grešku -->
    <div v-if="showErrorModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeErrorModal">&times;</span>
        <h2>Greška</h2>
        <p>{{successMessage }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>

</script>

<style scoped>

</style>