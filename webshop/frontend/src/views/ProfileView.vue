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

<script setup>

</script>

<style scoped>

</style>