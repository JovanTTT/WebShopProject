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
import axios from "axios";

export default {
  name: 'SellerProfileView',

  data() {
    return {
      kupac: {
        ime: '',
        prezime: '',
        korisnickoIme: '',
        telefon: '',
        opisKorisnika: '',
        slika: '', // Dodato polje za sliku
        kupljeniProizvodi: [],
        dobijeneRecenzije: [],
        prosecnaOcena: 0
      },
      user: null,
      isSeller: false,
      canRateCustomer: false,
      showRateCustomerModal: false,
      ocenaKupca: 1,
      komentarKupca: '',
      showErrorModal: false,
      showComplaintModal: false,
      prijavaText: '',
      successMessage: ''
    };
  },
  mounted() {

    const kupacId = this.$route.params.id;

    axios.get(`http://localhost:8080/api/user/profileView/${kupacId}`, { withCredentials: true })
        .then(response => {
          this.kupac = response.data;
        })
        .catch(error => {
          console.error('Greška pri dobijanju profila prodavca:', error);
        });
    this.fetchAverageRating(kupacId);

  },
  methods: {

    checkUserType() {

      this.user = JSON.parse(localStorage.getItem('user'));

      if (this.user && this.user.uloga === 'PRODAVAC') {
        this.isSeller = true;
      }
    },
    closeRateCustomerModal() {

      this.showRateCustomerModal = false;
      this.ocenaProdavca = 1;
      this.komentarProdavca = '';
    },
    closeErrorModal() {

      this.showErrorModal = false; // Zatvaranje grešnog modalnog prozora
    },
    closeComplaintModal() {

      this.showComplaintModal = false;
      this.prijavaText = '';
    },
    fetchAverageRating(kupacId) {

      axios.get(`http://localhost:8080/api/user/averageRatingBuyer/${kupacId}`, { withCredentials: true })
          .then(response => {
            this.kupac.prosecnaOcena = response.data.toFixed(1);
          })
          .catch(error => {
            console.error('Greška pri dobijanju prosečne ocene prodavca:', error);
          });
    },
    submitRating() {

      const kupacId = this.$route.params.id;

      axios.post('http://localhost:8080/api/user/rateBuyer/'+ kupacId + '?ocena=' + this.ocenaProdavca +'&komentar=' + this.komentarProdavca, {}, {withCredentials: true})
          .then(response => {
            console.log('Ocena uspešno poslata:', response.data);
            this.closeRateCustomerModal();
          })
          .catch(error => {
            if (error.response && error.response.data === 'Prodavac može da oceni kupca samo ako je prodao proizvod tom kupcu.') {
              this.successMessage= "Prodavac može da oceni kupca samo ako je prodao proizvod tom kupcu.";
              this.showErrorModal = true;
            } else {
              console.error('Nepoznata greška:', error);
            }
          });
    },
    submitComplaint() {

      const kupacId = this.$route.params.id;
      const prijavaRequestDTO = {
        razlogPrijave: this.prijavaText
      };

      axios.post(`http://localhost:8080/api/report/sellerRequest/${kupacId}`, prijavaRequestDTO, { withCredentials: true })
          .then(response => {
            console.log('Prijava uspešno poslata:', response.data);
            this.closeComplaintModal();
          })
          .catch(error => {
            if (error.response && error.response.data==="Prodavac može da da recenziju onom kupcu, koji je od njega kupio proizvod.") {
              this.successMessage= "Prodavac može da da recenziju onom kupcu, koji je od njega kupio proizvod..";
              this.showErrorModal = true;
            } else {
              console.error('Nepoznata greška:', error);
            }
          });
    }
  }
};
</script>

<style scoped>

</style>