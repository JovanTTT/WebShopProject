<template>
  <div>
    <div class="brand">
      <span class="letter">J</span><span class="letter">O</span><span class="letter">M</span><span class="letter">I</span>
    </div>
    <button class="btn-close" v-on:click="closeProductDetails"></button>
    <div class="product-details">
      <div class="product-image">
        <img :src="product.slikaProizvoda" alt="Product Image">
      </div>
      <div class="product-info">
        <hr color="#79d3b2">
        <h1>{{ product.naziv }}</h1>
        <hr color="#79d3b2" >
        <br>
        <br>
        <p>Opis proizvoda: {{ product.opis }}</p>
        <p>Tip prodaje: {{ product.tipProdaje}}</p>
        <div v-if="product && product.prodavac">
          <p @click="goToSellerProfile(product.prodavac.id)"  class="seller-name">Prodavac: {{ product.prodavac.korisnickoIme }}</p>
        </div>
        <p v-if="product.tipProdaje !== 'AUKCIJA'">Cena: {{ product.cena }} RSD</p>
        <div v-if="product.tipProdaje === 'AUKCIJA'">
          <hr>
          <p>Unesite vašu ponudu:</p>
          <input type="number" v-model="novaPonuda" />
        </div>
        <div class="buy-button-container">
          <button class="my-custom-button"  @click="buyProduct">Kupi proizvod</button>
        </div>
      </div>
    </div>
    <div v-if="showLoginModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeLoginModal">&times;</span>
        <p>{{ successMessage }}</p>
        <button @click="redirectToLogin">Prijavi se</button>
      </div>
    </div>

    <div v-if="showSuccessModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeSuccessModal">&times;</span>
        <p>{{ successMessage }}</p>
        <button @click="closeSuccessModal">Zatvori</button>
      </div>
    </div>

    <div v-if="showAlreadyPurchasedModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeAlreadyPurchasedModal">&times;</span>
        <p>Proizvod je već kupljen.</p>
        <button @click="closeAlreadyPurchasedModal">Zatvori</button>
      </div>
    </div>

    <div v-if="showSuccessModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeLowBidModal">&times;</span>
        <p>{{ successMessage }}</p>
        <button @click="closeSuccessModal">Zatvori</button>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';


export default {
  name: 'ProductDetails',
  data() {
    return {
      product: {
        prodavac: {}
      },
      showLoginModal: false,
      showSuccessModal: false,
      showAlreadyPurchasedModal: false,
      showLowBidModal: false,
      novaPonuda: 1,
      successMessage: ''
    };
  },
  mounted:function() {

    const productId = this.$route.params.id;

    axios.get("http://localhost:8080/api/product/" + productId)
        .then(response => {
          this.product = response.data;
        })
        .catch(error => {
          console.error('Greška pri dobijanju detalja proizvoda:', error);
        });
  },
  methods: {
    closeProductDetails() {

      this.$router.push('/');
    },
    buyProduct() {

      const user = JSON.parse(localStorage.getItem('user'));
      if (!user) {
        this.successMessage="Morate biti prijavljeni da bi ste kupili proizvod.";
        this.showLoginModal = true;
      } else {
        console.log("Korisnik je ulogovan. Nastavlja se sa kupovinom...");

        if (this.product.tipProdaje === 'FIKSNA') {

          axios.post(`http://localhost:8080/api/user/shopNowFixedPrice/${this.product.id}`, {}, {
            withCredentials: true
          })
              .then(response => {
                console.log(this.product.id);
                console.log("Proizvod uspešno kupljen:", response.data);
                this.successMessage = "Uspešno ste kupili proizvod!";
                this.showSuccessModal = true;
              })
              .catch(error => {
                if(error.response.data ==="Samo kupac može da kupuje."){
                  this.successMessage = "Samo kupac može da kupuje.";
                  this.showSuccessModal = true;
                }else {

                  console.error('Greška pri kupovini proizvoda:', error);
                  this.successMessage = "Greška pri kupovini proizvoda.";
                  this.showSuccessModal = true;
                }
              });
        }else if (this.product.tipProdaje === 'AUKCIJA') {

          axios.post(`http://localhost:8080/api/user/shopNowAuction`, {}, {
            params: {
              id: this.product.id,
              novaPonuda: this.novaPonuda
            },
            withCredentials: true
          })
              .then(response => {
                console.log(this.product.id);
                console.log("Ponuda uspešno prihvaćena:", response.data);
                this.successMessage = "Vaša ponuda je prihvaćena!";
                this.showSuccessModal = true;
              })
              .catch(error => {
                console.error('Greška pri postavljanju ponude:', error);
                console.log('Error response:', error.response);
                if (error.response && typeof error.response.data === 'string') {
                  if (error.response.data === 'Proizvod je već prodat.') {
                    this.showAlreadyPurchasedModal = true;
                  } else if (error.response.data.startsWith('Ponuda koju ste poslali se nije uvažila jer je drugi korisnik poslao veću: ')) {
                    this.successMessage =error.response.data;
                    this.showSuccessModal = true;
                  } else if(error.response.data ==="Samo kupci mogu da daju ponude."){
                    this.successMessage = "Samo kupci mogu da daju ponude.";
                    this.showSuccessModal = true;
                  }
                } else {
                  this.successMessage = "Došlo je do greške pri postavljanju ponude.";
                  this.showSuccessModal = true;
                }
              });
        }
      }
    },
    closeLoginModal() {

      this.showLoginModal = false;
    },
    redirectToLogin() {

      this.$router.push('/login');
    },
    closeSuccessModal() {

      this.showSuccessModal = false;
    },
    closeAlreadyPurchasedModal() {

      this.showAlreadyPurchasedModal = false;
    },
    closeLowBidModal() {

      this.showLowBidModal = false;
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
  }
};
</script>
<style scoped>
.btn-close {
  position: absolute;
  top: 10px;
  left: 10px;
  color: rgba(47, 128, 102, 0.76);
  cursor: pointer;
  background: transparent;
  border: none;
  font-size: 24px;
  font-weight: bold;
  outline: none;
  z-index: 1000;  /*Osigurava da je dugme iznad drugih elemenata */
}

.btn-close:hover {
  color: red; /* Boja dugmeta pri prelasku mišem */
}
.product-details {
  font-family: Arial;
  display: flex;
  margin-top: 50px; /* Razmak na vrhu */
  margin-right: 50px;
}

.product-image {
  margin-left: 50px;
  flex: 0 0 auto; /* Skroz levo */
  margin-right: 20px; /* Razmak između slike i informacija */
  max-width: 500px; /* Maksimalna širina slike */
}

.product-info {
  flex: 1; /* Ostatak prostora zauzima informacije */
  font-size: 30px; /* Primer uvećanja fonta na 18px */
}

.product-image img {
  max-width: 100%; /* Slika se skalira na maksimalnu širinu */
  height: auto; /* Visina se automatski prilagođava */
}

.product-info h1 {
  font-size: 24px;
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  color: #000000;

}

.product-info p {
  font-size: 18px;
  line-height: 1.6;
  margin-bottom: 10px;
  justify-content: center; /* Centriranje po vertikali */
}

.buy-button-container {
  display: flex;
  justify-content: flex-end; /* Poravnanje na kraj */
  align-items: flex-end; /* Poravnanje na dno */
  margin-top: 150px; /* Pomera kontejner na dno, ako je potrebno */
  margin-right: 300px; /* Margina za razmak od desnog kraja */
  margin-bottom: 400px; /* Margina za razmak od donjeg kraja */
}
.my-custom-button {

  width: 300px; /* Širina dugmeta */
  cursor: pointer;
  border-radius: 200px;
  background-color: rgba(47, 128, 102, 0.76);
}
.my-custom-button:hover{
  background-color: rgb(72, 136, 113);
}
.brand {
  font-family: Arial;
  margin-top: 20px;
  margin-left:1300px ;
}
.modal {
  display: block; /* Prikaži modal */
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.4);

}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 300px;
  text-align: center;
  border-radius: 20px;
}
.seller-name {
  color: #c963c9; /* Boja teksta */
  cursor: pointer; /* Promena kursora na pokazivač */
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;

}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

</style>