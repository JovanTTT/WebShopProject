<template>
  <div>
    <div class="user-profile">
      <h2>Vaši podaci</h2>
      <div v-if="loading">Učitavanje...</div>
      <div v-else>
        <img :src="currentUser.slika" alt="Slika korisnika" class="slika"/><br>
        <div class="form-container">
          <div class="column">
            <label for="ime">Ime:</label>
            <input v-model="currentUser.ime" type="text" id="ime" placeholder="Unesite novo ime" /><br>
            <label for="prezime">Prezime:</label>
            <input v-model="currentUser.prezime" type="text" id="prezime" placeholder="Unesite novo prezime" /><br>
            <label for="korisnickoIme">Korisničko ime:</label>
            <input v-model="currentUser.korisnickoIme" type="text" id="korisnickoIme" placeholder="Unesite novo korisničko ime" /><br>
          </div>
          <div class="column">
            <label for="mejl">Mejl:</label>
            <input v-model="currentUser.mejl" type="text" id="mejl" placeholder="Unesite novi mejl" /><br>
            <label for="telefon">Telefon:</label>
            <input v-model="currentUser.telefon" type="text" id="telefon" placeholder="Unesite novi telefon" /><br>
            <label for="datumRodjenja">Datum rođenja:</label>
            <input v-model="currentUser.datumRodjenja" type="date" id="datumRodjenja" placeholder="Unesite novi datum rođenja" /><br>
          </div>
          <div class="column">
            <label for="opisKorisnika">Opis:</label>
            <input v-model="currentUser.opisKorisnika" type="text" id="opisKorisnika" placeholder="Unesite nov opis" /><br>
            <label for="slika">Slika:</label>
            <input v-model="currentUser.slika" alt="Slika korisnika" type="text" id="slika" placeholder="Unesite novu sliku" /><br>
            <label for="uloga">Uloga:</label>
            <select v-model="currentUser.uloga" id="uloga" name="uloge" required>
              <option value="PRODAVAC">Prodavac</option>
              <option value="KUPAC">Kupac</option>
            </select>
          </div>
          <div class="column password-role-container">
            <label for="staraLozinka">Stara lozinka:</label>
            <input v-model="currentUser.staraLozinka" type="password" id="staraLozinka" placeholder="Unesite staru lozinku" required /><br>
            <div class="novaLozinka">
              <label for="novaLozinka">Nova lozinka:</label>
              <input v-model="currentUser.novaLozinka" type="password" id="novaLozinka" placeholder="Unesite novu lozinku" /><br>
            </div>
            <div class="updateDugme">
              <button v-on:click="update">Update</button>
            </div>
          </div>
        </div>
        <button @click="recenzije">Recenzije</button>
        <div v-if="showReviews">
          <div v-for="review in reviews" :key="review.id" class="review">
            <h3>{{ currentUser.uloga === 'PRODAVAC' ? 'Recenzije:' : 'Recenzije:' }}</h3>
            <p>Ime: {{ currentUser.uloga === 'PRODAVAC' ? review.kupacKojemSamDaoRecenziju.ime : review.prodavacKojemSamDaoRecenziju.ime }}</p>
            <p>Prezime: {{ currentUser.uloga === 'PRODAVAC' ? review.kupacKojemSamDaoRecenziju.prezime : review.prodavacKojemSamDaoRecenziju.prezime }}</p>
            <p>Korisničko ime: {{ currentUser.uloga === 'PRODAVAC' ? review.kupacKojemSamDaoRecenziju.korisnickoIme : review.prodavacKojemSamDaoRecenziju.korisnickoIme }}</p>
            <p>Datum podnošenja recenzije: {{ formatDate(review.datumPodnosenjaRecenzije) }}</p>
            <p>Ocena: {{ review.ocena }}</p>
            <p>Komentar: {{ review.komentar }}</p>
          </div>
        </div>
      </div>
    </div>
    <div v-if="currentUser.uloga === 'KUPAC'" class="product-section">
      <h2>Kupljeni proizvodi</h2>
      <ul>
        <li v-for="proizvod in kupljeniProizvodi" :key="proizvod.id"  @click="prikaziDetaljeProizvoda(proizvod.id)" class="ime-proizvoda">
          <div>
            <p>Naziv: {{ proizvod.naziv }}</p>
          </div>
        </li>
      </ul>
    </div>
    <div v-else-if="currentUser.uloga === 'PRODAVAC'" class="product-section">
      <h2>Proizvodi na prodaju</h2>
      <ul>
        <li v-for="proizvod in proizvodiNaProdaju" :key="proizvod.id" @click="prikaziDetaljeProizvoda(proizvod.id)" class="ime-proizvoda">
          <div>
            <p class="product-name-user-name">Naziv: {{ proizvod.naziv }}</p>
          </div>
        </li>
      </ul>
    </div>
    <div v-if="showModal" class="modal" @click.self="closeModal">
      <div class="modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <div v-if="selectedProduct">
          <h3>Detalji proizvoda:</h3>
          <img :src="selectedProduct.slikaProizvoda" alt="Slika proizvoda" class="product-image">
          <p>Naziv: {{ selectedProduct.naziv }}</p>
          <p>Cena: {{ selectedProduct.cena }}</p>
          <p>Opis: {{ selectedProduct.opis }}</p>
          <p>Tip prodaje: {{ selectedProduct.tipProdaje }}</p>
          <div v-if="currentUser.uloga==='KUPAC' && selectedProduct.prodavac">
            <p @click="goToSellerProfile(selectedProduct.prodavac.id)"  class="product-name-user-name">Prodavac: {{ selectedProduct.prodavac.korisnickoIme }}</p>
          </div>
          <div v-if="currentUser.uloga==='PRODAVAC' && selectedProduct.kupac">
            <p @click="goToCustomerProfile(selectedProduct.kupac.id)"  class="product-name-user-name">Kupac: {{ selectedProduct.kupac.korisnickoIme }}</p>
          </div>
          <button v-if="currentUser.uloga === 'PRODAVAC' && selectedProduct.kupac===null" @click="editProduct" class="button-accept">Ažuriraj proizvod</button>
          <button v-if="currentUser.uloga === 'PRODAVAC' && selectedProduct.tipProdaje === 'AUKCIJA'"
                  @click="endAuction(selectedProduct.id)" class="button-accept" >
            Završi aukciju
          </button>
        </div>
      </div>
    </div>
    <div v-if="showEditModal" class="modal" @click.self="closeEditModal">
      <div class="modal-content">
        <span class="close" @click="closeEditModal">&times;</span>
        <div v-if="selectedProduct">
          <h3>Ažuriraj proizvod:</h3>
          <form @submit.prevent="updateProduct">
            <div>
              <label for="naziv">Naziv:</label>
              <input type="text" v-model="selectedProduct.naziv" id="naziv" required>
            </div>
            <div>
              <label for="cena">Cena:</label>
              <input type="number" v-model="selectedProduct.cena" id="cena" required>
            </div>
            <div>
              <label for="opis">Opis:</label>
              <textarea v-model="selectedProduct.opis" id="opis" required></textarea>
            </div>
            <div>
              <label for="slikaProizvoda">Slika proizvoda (URL):</label>
              <input type="text" v-model="selectedProduct.slikaProizvoda" id="slikaProizvoda" required>
            </div>
            <div>
              <label for="tipProdaje">Tip prodaje:</label>
              <select v-model="selectedProduct.tipProdaje" id="tipProdaje" required>
                <option value="PRODAJA">Prodaja</option>
                <option value="AUKCIJA">Aukcija</option>
              </select>
            </div>
            <button type="submit">Sačuvaj izmene</button>
          </form>
        </div>
      </div>
    </div>
    <div v-if="showSuccessModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeSuccessModal">&times;</span>
        <p>{{ successMessage }}</p>
        <button @click="closeSuccessModal" class="button-accept">Zatvori</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      currentUser: {
        ime: '',
        prezime: '',
        korisnickoIme: '',
        mejl: '',
        telefon: '',
        staraLozinka: '',
        novaLozinka: '',
        datumRodjenja: '',
        uloga: '',
        slika: '',
        opisKorisnika: ''
      }, // Objekat za čuvanje podataka o korisniku
      loading: true, // Prikazivanje loadera dok se podaci učitavaju
      showReviews: false,
      reviews: [],
      proizvodiNaProdaju: [],
      kupljeniProizvodi:[],
      user: null,
      userRole: '',
      selectedProduct: null,
      showModal: false,
      showEditModal: false,
      userUSER:null,
      showSuccessModal: false,
    };
  },
  mounted() {
    // Pozivamo metodu za dobavljanje trenutnog korisnika kada se komponenta montira
    this.fetchCurrentUser();
    this.fetchProducts();
    //this.fetchReviews();

  },
  methods: {
    fetchCurrentUser() {
      axios
          .get('http://localhost:8080/api/user/currentUser', {withCredentials: true})
          .then(response => {
            this.currentUser = response.data;
            console.log("JOVANOVA METODA",this.currentUser);
            this.loading = false;
          })
          .catch(error => {
            console.error('Greška pri dobavljanju podataka korisnika:', error);
            this.loading = false;
          });
    },
    update(){
      if(this.currentUser.staraLozinka == null && this.currentUser.novaLozinka == null){
        alert('Morate uneti trenutnu lozinku');
      } else if(this.currentUser.staraLozinka == null){
        alert('Morate uneti trenutnu lozinku');
      }else if(this.currentUser.uloga == null){
        alert('Morate izabrati ulogu');
      }else if(this.currentUser.novaLozinka == null){
        alert('Ponovo unesite staru lozinku ili unesite novu!');
      }
      console.log('Trenutni podaci o korisniku pre slanja:', this.currentUser);
      axios
          .put(`http://localhost:8080/api/user/updateSeller/${this.currentUser.id}`, this.currentUser, {
            withCredentials: true,
            headers: {
              'Content-Type': 'application/json'
            }
          })
          .then(response => {
            this.currentUser = response.data;
            this.loading = false;
          })
          .catch(error => {
            console.error('Greška pri dobavljanju podataka korisnika:', error);
            this.loading = false;
          });
    },

    recenzije(){
      this.showReviews = !this.showReviews;
      console.log('Toggle Reviews:', this.showReviews);
      if (this.showReviews && this.reviews.length === 0) {
        this.fetchReviews();
      }
    },
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    },
    fetchReviews(){
      let endpoint;
      console.log(this.currentUser.uloga);
      if(this.currentUser.uloga === 'KUPAC'){
        endpoint = 'http://localhost:8080/api/user/reviewedSellers/received';
      } else if(this.currentUser.uloga === 'PRODAVAC'){
        endpoint = 'http://localhost:8080/api/user/reviewedBuyer/received';
      }else{
        console.error('Nepoznata uloga korisnika', this.currentUser);
        return;
      }

      axios
          .get(endpoint, {
            withCredentials: true,
            headers: {
              'Content-Type': 'application/json'
            }
          })
          .then(response => {
            console.log('Fetched Reviews:', response.data);
            this.reviews = response.data;
          })
          .catch(error => {
            console.error('Greška pri dobavljanju podataka o recenzijama:', error);
          });
    },
    fetchProducts() {
      console.log("NJANJA");
      const user = JSON.parse(localStorage.getItem('user'));
      console.log(user);
      const userRole = localStorage.getItem("userRole");
      console.log("ULOGA",userRole);
      let endpoint;
      if (userRole === 'KUPAC') {
        endpoint = `http://localhost:8080/api/user/products/${user}`;
      } else if (userRole === 'PRODAVAC') {
        endpoint = `http://localhost:8080/api/user/products/${user}`;
      } else {
        console.error('Nepoznata uloga korisnika:',userRole   );
        return;
      }

      axios.get(endpoint, { withCredentials: true })
          .then(response => {
            console.log(response);
            if (userRole   === 'KUPAC') {
              this.kupljeniProizvodi = response.data;
            } else if (userRole === 'PRODAVAC') {
              this.proizvodiNaProdaju = response.data; // Update proizvodiNaProdaju
            }
          })
          .catch(error => {
            console.error('Greška pri dobavljanju podataka o proizvodima:', error);
          });
    },
    prikaziDetaljeProizvoda(id) {
      console.log("OVO JE PROIZVOD",id);
      axios.get(`http://localhost:8080/api/product/${id}`, { withCredentials: true })
          .then(response => {
            console.log('Detalji proizvoda:', response.data);
            this.showModal = true;
            this.selectedProduct = response.data;
          })
          .catch(error => {
            console.error('Greška pri dobavljanju detalja proizvoda:', error);
          });
    },
    closeModal() {
      this.showModal = false;
      this.selectedProduct = null;
    },
    editProduct() {
      this.showEditModal = true;
      this.showModal = false;
    },
    closeEditModal() {
      this.showEditModal = false;
    },
    closeSuccessModal() {
      this.showSuccessModal = false;
    },
    updateProduct() {
      const id = this.selectedProduct.id;

      axios
          .put(`http://localhost:8080/api/product/update/${id}`, this.selectedProduct, {
            withCredentials: true,
            headers: {
              'Content-Type': 'application/json'
            }
          })
          .then(response => {
            this.successMessage = "Proizvod je uspešno ažuriran.";
            this.showSuccessModal = true;
            this.closeEditModal();
            this.fetchProducts();
          })
          .catch(error => {
            if (error.response && error.response.data === "Proizvod je prodat.") {
              this.successMessage = "Prodat proizvod ne može da se menja.";
              this.showSuccessModal = true;
              console.log(error);
            } else if (error.response && error.response.data === "Proizvod se ne može izmeniti jer postoje aktivne ponude u aukciji.") {
              this.successMessage = "Proizvod se ne može izmeniti jer postoje aktivne ponude u aukciji.";
              this.showSuccessModal = true;
              console.log(error);
            } else {
              console.log(error);
              this.successMessage = "Došlo je do greške.";
              this.showSuccessModal = true;
            }
          });
    },
    endAuction(id) {
      axios.put(`http://localhost:8080/api/product/endAuction/${id}`, {}, { withCredentials: true })
          .then(response => {
            console.log('Aukcija je uspešno završena:', response.data);
            this.successMessage = "Aukcija je uspešno završena.";
            this.showSuccessModal = true;
          })
          .catch(error => {
            if (error.response && error.response.data === "Prodavac nema traženi proizvod na prodaju.") {
              this.successMessage = "Prodavac nema traženi proizvod na prodaju.";
              this.showSuccessModal = true;
            }else if(error.response && error.response.data === "Ne postoje ponude."){
              this.successMessage = "Ne postoje ponude.";
              this.showSuccessModal = true;
            }else if(error.response && error.response.data === "Aukcija nije aktivna ili nema ponuda."){
              this.successMessage = "Aukcija nije aktivna ili nema ponuda.";
              this.showSuccessModal = true;
            }
            else {
              console.error('Greška pri završavanju aukcije:', error);
              this.successMessage = "Greška pri završavanju aukcije:";
              this.showSuccessModal = true;
            }
          });
    },
    goToSellerProfile(prodavacId) {
      const user = JSON.parse(localStorage.getItem('user'));
      if (user) {
        this.$router.push(`/sellerProfile/${prodavacId}`);
      } else {
        this.successMessage="Morate biti prijavljeni da bi ste mogli da vidite profil prodavca."
        this.showLoginModal = true;
      }
    },
    goToCustomerProfile(kupacId) {
      const user = JSON.parse(localStorage.getItem('user'));
      if (user) {
        this.$router.push(`/customerProfile/${kupacId}`);
      } else {
        this.successMessage="Morate biti prijavljeni da bi ste mogli da vidite profil kupca."
        this.showLoginModal = true;
      }
    },
  }
};
</script>

<style scoped>
.user-profile {
  max-width: 1200px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f0f0f0;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  position: relative;
}

.user-profile h2 {
  text-align: center;
}

.slika {
  position: absolute;
  top: 20px;
  left: 20px;
  width: 150px;
  height: auto;
  border-radius: 50%;
}

.novaLozinka {
  margin-top: -18px;
}

.form-container {
  display: flex;
  justify-content: space-between;
  margin-left: 200px;
}

.column {
  flex: 1;
  margin: 0 10px;
}

.password-role-container {
  margin-top: 0px;
  display: flex;
  flex-direction: column;
}

.user-profile label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.user-profile input[type="text"],
.user-profile input[type="password"],
.user-profile input[type="date"],
.user-profile select {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 3px;
  box-sizing: border-box;
}

.user-profile button {
  width: calc(100% - 20px);
  padding: 12px 20px;
  margin-top: 20px;
  background-color: rgba(47, 128, 102, 0.76);
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.user-profile button:hover {
  background-color: rgb(72, 136, 113);
}

/* Dodati stilovi za sekciju recenzija */
.review {
  background-color: #ffffff;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 20px;
  margin-top: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.review h3 {
  margin-top: 0;
}

.review p {
  margin: 5px 0;
}

.updateDugme {
  margin-left: 20px;
}
.product-section {
  margin-top: 20px;
  background-color: #f9f9f9;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 10px;
  box-shadow: 0 2px 5px rgba(47, 128, 102, 0.76);
  color: rgba(47, 128, 102, 0.76);
}

.product-section h2 {
  text-align: center;
  font-size: 22px;
  margin-bottom: 10px;
}

.product-list {
  list-style-type: none;
  padding: 0;
}

.product-list li {

  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.product-list li:last-child {
  border-bottom: none;


}

.product-list li:hover {
  background-color: #f0f0f0;
  cursor: pointer;
}
.modal {
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(255, 255, 255, 0.26);
  color: rgb(119, 53, 119);

}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  width: 50%;
  max-width: 600px;
  box-shadow: 0 5px 15px rgba(47, 128, 102, 0.76);
  text-align: center;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}
.product-image {
  max-width: 50%;
  height: auto;
  margin-bottom: 20px;
}
.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.modal-content h3 {
  text-align: center;
  margin-bottom: 20px;
}

.modal-content form {
  display: flex;
  flex-direction: column;
}

.modal-content label {
  margin-bottom: 5px;
  font-weight: bold;
}

.modal-content input[type="text"],
.modal-content input[type="number"],
.modal-content textarea,
.modal-content select {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 50px;
  box-sizing: border-box;
}

.modal-content button[type="submit"] {
  width: 100%;
  padding: 12px 20px;
  margin-top: 20px;
  background-color: rgba(47, 128, 102, 0.76);
  color: white;
  border: none;
  border-radius: 50px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.modal-content button[type="submit"]:hover {
  background-color: rgba(47, 128, 102, 0.76);
}

.modal-backdrop {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent background */
  z-index: 900; /* Lower than modal content, but higher than page content */
}

.button-accept {
  background-color: rgba(47, 128, 102, 0.76); /* Zelena boja */
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 50px; /* Radijus 50% širine dugmeta */
  cursor: pointer;
  font-size: 16px;
  margin-right: 10px; /* Dodaj marginu sa desne strane */
}

.button-accept:hover {
  background-color: #488871; /* Tamnija nijansa zelene boje pri hoveru */
}

.button-accept:focus {
  outline: none; /* Uklanja outline prilikom fokusa */
}
.product-name-user-name:hover {
  cursor: pointer;
}

.ime-proizvoda {
  cursor: pointer;
}
</style>