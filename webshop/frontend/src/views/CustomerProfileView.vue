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

    <div v-if="showRateCustomerModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeRateCustomerModal">&times;</span>
        <h2>Oceni kupac</h2>
        <form @submit.prevent="submitRating">
          <label for="ocena">Ocena:</label>
          <input type="number" id="ocena" v-model="ocenaProdavca" min="1" max="5" required>
          <label for="komentar">Komentar:</label>
          <textarea id="komentar" v-model="komentarProdavca"></textarea>
          <button type="submit">Pošalji ocenu</button>
        </form>
      </div>
    </div>

    <button v-if="checkUserType" @click="showComplaintModal = true" class="submit-button">Prijavi kupca</button>

    <div v-if="showComplaintModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeComplaintModal">&times;</span>
        <h2>Prijavi kupca</h2>
        <form @submit.prevent="submitComplaint">
          <label for="prijava">Prijava:</label>
          <textarea id="prijava" v-model="prijavaText" required></textarea>
          <button type="submit">Pošalji prijavu</button>
        </form>
      </div>
    </div>

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