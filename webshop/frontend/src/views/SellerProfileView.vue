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

<script>
import axios from 'axios';

export default {
  name: 'SellerProfileView',
  data() {
    return {
      prodavac: {
        ime: '',
        prezime: '',
        korisnickoIme: '',
        telefon: '',
        opisKorisnika: '',
        slika: '', // Dodato polje za sliku
        proizvodiNaProdaju: [],
        dobijeneRecenzije: [],
        prosecnaOcena: 0
      },
      user: null,
      isBuyer: false,
      canRateSeller: false,
      showRateSellerModal: false,
      ocenaKupca: 1,
      komentarKupca: '',
      showErrorModal: false,
      showComplaintModal: false,
      prijavaText: '',
      successMessage: ''
    };
  },
  mounted() {
    const prodavacId = this.$route.params.id;
    axios.get(`http://localhost:8080/api/user/profileView/${prodavacId}`, { withCredentials: true })
        .then(response => {
          this.prodavac = response.data;
        })
        .catch(error => {
          console.error('Greška pri dobijanju profila prodavca:', error);
        });
    this.fetchAverageRating(prodavacId);

  },
  methods: {
    checkUserType() {
      this.user = JSON.parse(localStorage.getItem('user'));
      if (this.user && this.user.uloga === 'KUPAC') {
        this.isBuyer = true;
      }
    },
    closeRateSellerModal() {
      this.showRateSellerModal = false;
      this.ocenaKupca = 1;
      this.komentarKupca = '';
    },
    closeErrorModal() {
      this.showErrorModal = false; // Zatvaranje grešnog modalnog prozora
    },

    closeComplaintModal() {
      this.showComplaintModal = false;
      this.prijavaText = '';
    },
    fetchAverageRating(prodavacId) {

      axios.get(`http://localhost:8080/api/user/averageRatingSeller/${prodavacId}`, { withCredentials: true })
          .then(response => {
            this.prodavac.prosecnaOcena = response.data.toFixed(1);
          })
          .catch(error => {
            console.error('Greška pri dobijanju prosečne ocene prodavca:', error);
          });
    },
    submitRating() {

      const prodavacId = this.$route.params.id;
      console.log(prodavacId);

      axios.post('http://localhost:8080/api/user/rateSeller/'+ prodavacId + '?ocena=' + this.ocenaKupca +'&komentar=' + this.komentarKupca, {}, {withCredentials: true})
          .then(response => {
            console.log('Ocena uspešno poslata:', response.data);
            this.closeRateSellerModal();
          })
          .catch(error => {
            if (error.response && error.response.data === 'Kupac može da oceni prodavca samo ako je kupio proizvod od tog prodavca.') {
              // Prikaži modalski dijalog ili skočni prozor
              this.successMessage= "Ne možete oceniti prodavca od kog niste kupili proizvod.";
              this.showErrorModal = true;
            } else {
              console.error('Nepoznata greška:', error);
            }
          });
    },
    submitComplaint() {
      const prodavacId = this.$route.params.id;
      const prijavaRequestDTO = {
        razlogPrijave: this.prijavaText
      };
      axios.post(`http://localhost:8080/api/report/customerRequest/${prodavacId}`, prijavaRequestDTO, { withCredentials: true })
          .then(response => {
            console.log('Prijava uspešno poslata:', response.data);
            this.closeComplaintModal();
          })
          .catch(error => {
            if (error.response && error.response.data==="Kupac može da prijavi onog prodavca od kog je kupio proizvod") {
              console.error('Ne moze da prijavi jer on nije kupio proizvod od tog prodavca', error.response.data);
              this.successMessage= "Ne možete da prijavite prodavca od kog niste kupili proizvod.";
              this.showErrorModal = true;
            }else if(error.response && error.response.data==="Korisnik koji nije kupac, ne može da prijavi prodavca."){
              this.successMessage= "Korisnik koji nije kupac, ne može da prijavi prodavca.";
              this.showErrorModal = true;
            }
            else {

              console.error('Nepoznata greška:', error);
            }
          });
    }
  }
};
</script>

<style scoped>
.seller-profile {
  font-family: Arial, sans-serif;
  margin: 20px auto;
  padding: 20px;
  max-width: 600px;
  border: 2px solid #488871;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.brand {
  font-size: 2em;
  margin-bottom: 10px;
  text-align: center;
  color: #488871;
}

.seller-image {
  text-align: center;
  margin-bottom: 20px;
}

.seller-image img {
  width: 150px;
  height: 150px;
  border-radius: 10px;
  object-fit: cover;
  margin-bottom: 10px;
}

.basic-info {
  margin-bottom: 20px;
}

.products {
  margin-bottom: 20px;
}

.reviews {
  margin-bottom: 20px;
  background-color: #ffffff;
  padding: 10px;
  border-radius: 5px;
}

.reviews li {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: rgba(187, 224, 208, 0.84);
}

.username {
  text-align: center;
  font-weight: bold;
  font-size: 1.2em;
}

button.rate-button, button.submit-button {
  display: block;
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  background-color: rgba(47, 128, 102, 0.76);
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 1em;
  cursor: pointer;
}

button.rate-button:hover, button.submit-button:hover {
  background-color: #357a61;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 5px;
  width: 90%;
  max-width: 500px;
  text-align: center;
}

.modal-content .close {
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
  font-size: 1.5em;
  color: #aaa;
}

.modal-content .close:hover {
  color: #000;
}

form {
  display: flex;
  flex-direction: column;
}

form label {
  margin-top: 10px;
  text-align: left;
}

form input, form textarea {
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

form button {
  margin-top: 20px;
  background-color: rgba(47, 128, 102, 0.76);
  border-radius: 50px;
}
button.rate-button, button.submit-button {
  display: block;
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  background-color: rgba(47, 128, 102, 0.76); /* Zelena boja */
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 1em;
  cursor: pointer;
}

button.rate-button:hover, button.submit-button:hover {
  background-color: #357a61; /* Tamnija nijansa zelene boje prilikom hovera */
}

</style>