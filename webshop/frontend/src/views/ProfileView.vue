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

  </div>
</template>

<script setup>

</script>

<style scoped>

</style>